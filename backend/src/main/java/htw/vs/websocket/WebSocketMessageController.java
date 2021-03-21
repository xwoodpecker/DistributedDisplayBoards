package htw.vs.websocket;

import htw.vs.base.Config;
import htw.vs.base.Const;
import htw.vs.data.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;


/**
 * The type Web socket message controller.
 */
@Controller
public class WebSocketMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final RoleRepository roleRepository;


    private Map<Long, List<Message>> boardMessages;


    /**
     * Initialize.
     */
    void initialize() {
        new CleanerThread().start();
    }

    /**
     * Instantiates a new Web socket message controller.
     *
     * @param simpMessagingTemplate the simp messaging template
     * @param boardRepository       the board repository
     * @param userRepository        the user repository
     * @param messageRepository     the message repository
     * @param roleRepository        the role repository
     */
    public WebSocketMessageController(SimpMessagingTemplate simpMessagingTemplate, BoardRepository boardRepository, UserRepository userRepository, MessageRepository messageRepository, RoleRepository roleRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.roleRepository = roleRepository;


        generateBoardMessages();
        initialize();
    }

    /**
     * Gets active messages.
     *
     * @param authentication the authentication
     * @param board          the board
     * @return the active messages
     */
    @MessageMapping("/getActiveMessages")
    public boolean getActiveMessages(Authentication authentication, @Payload Board board) {

        //todo: check if findbyId works as well
        board = this.boardRepository.findBoardByIdEagerGroup(board.getId());
        User user = this.userRepository.findUserByUserName(authentication.getName());

        verifyUserBoard(authentication, user, board);

        List<Message> messages = getAllByBoard(board);

        if(messages != null)
            sendToBoard(board.getId(),messages);

        return true;
    }


    /**
     * Message boolean.
     *
     * @param authentication the authentication
     * @param message        the message
     * @return the boolean
     */
//used for sending new message or editing a message
    @MessageMapping("/message")
    public boolean message(Authentication authentication,  @Payload Message message) {

        //todo: check if findbyId works as well
        message.setBoard(this.boardRepository.findBoardByIdEagerGroup(message.getBoard().getId()));

        User user = userRepository.findById(message.getUser().getId())
                .orElseThrow(() -> new AccessDeniedException(Const.USER_NOT_FOUND_EXCEPTION));

        message.setUser(user);

        verifyUserBoard(authentication, user, message.getBoard());

        List<Message> messages = addOrReplace(message);

        if(messages != null)
            sendToBoard(message.getBoard().getId(),messages);

        return true;
    }

    /**
     * Push message to central boolean.
     *
     * @param authentication the authentication
     * @param message        the message
     * @return the boolean
     */
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @MessageMapping("/pushMessage")
    public boolean pushMessageToCentral(Authentication authentication, @Payload Message message) {
        Message centralMsg = new Message(message);
        centralMsg.setId(null);
        Board centralBoard = boardRepository.findBoardByBoardName(Config.CENTRAL_BOARD_NAME);

        if(message.getBoard().getId() == centralBoard.getId())
            throw new IllegalArgumentException(Const.PUSH_MESSAGE_EXCEPTION);


        message.setBoard(this.boardRepository.findBoardByIdEagerGroup(message.getBoard().getId()));
        verifyCoordinator(authentication, message.getBoard().getGroup().getCoordinator());
        List<Message> messages = addOrReplace(centralMsg);


        if(messages != null)
            sendToBoard(centralBoard.getId(), messages);

        return true;
    }



    //todo : test
    private void verifyCoordinator(Authentication authentication, User coordinator) {
        if(coordinator == null)
            throw new AccessDeniedException(Const.USER_NOT_FOUND_EXCEPTION);

        User authenticatedUser = userRepository.findUserByUserName(authentication.getName());
        if(authenticatedUser.getRoles().stream().filter(r -> r.getName().equals(Const.SUPERVISOR_ROLE)).count() > 0)
            return;

        if (!coordinator.getUserName().equals(authentication.getName()))
            throw new AccessDeniedException(Const.VERIFY_USER_EXCEPTION);
    }

    //todo : test
    private void verifyUserBoard(Authentication authentication, User user, Board board) {
        if(user == null)
            throw new AccessDeniedException(Const.USER_NOT_FOUND_EXCEPTION);

        User authenticatedUser = userRepository.findUserByUserName(authentication.getName());
        Board centralBoard = boardRepository.findBoardByBoardName(Config.CENTRAL_BOARD_NAME);

        if(!(authenticatedUser.getRoles().stream().filter(r -> r.getName().equals(Const.SUPERVISOR_ROLE)).count() > 0)){ //is supervisor?
            if(!(centralBoard.getId().equals(board.getId()) && authenticatedUser.getRoles().stream().filter(r -> r.getName().equals(Const.COORDINATOR_ROLE)).count() > 0)){ //is centralboard & is coordinator
                if(!user.getUserName().equals(authentication.getName()))
                    throw new AccessDeniedException(Const.VERIFY_USER_EXCEPTION);
                if(!user.getGroups().stream().filter(g -> g.getBoard().getId() == board.getId()).findAny().isPresent())
                    throw new AccessDeniedException(Const.USER_BOARD_EXCEPTION);
            }
        }
    }


    /**
     * Send to board.
     *
     * @param boardId  the board id
     * @param messages the messages
     */
    public void sendToBoard(Long boardId, List<Message> messages) {
        simpMessagingTemplate.convertAndSend(
                Config.BASIC_TOPIC + boardId,
                messages);
    }


    private void generateBoardMessages(){
        List<Message> msgs = messageRepository.findMessagesToDisplay();
        boardMessages = msgs.parallelStream().collect(Collector.of(
                ConcurrentHashMap::new,
                (map, m) -> map.computeIfAbsent(m.getBoard().getId(), k -> {
                    CopyOnWriteArrayList l = new CopyOnWriteArrayList<>();
                    l.add(m);
                    return l;
                }),
                (a, b) -> {
                    b.forEach(( key, set ) -> a.computeIfAbsent(key, k -> new CopyOnWriteArrayList<>()).addAll(set));
                    return a;
                }
        ));
    }


    /**
     * todo: test
     * Add or replace list.
     *
     * @param msg the msg
     * @return the list
     */
    public List<Message> addOrReplace(Message msg) {
        msg = messageRepository.save(msg);
        List<Message> msgs = boardMessages.get(msg.getBoard().getId());
        if(msgs == null) {
            msgs = new CopyOnWriteArrayList<>();
            boardMessages.put(msg.getBoard().getId(), msgs);
        }
        Message finalMsg = msg;
        msgs.removeIf(m -> m.getId() == finalMsg.getId());
        if(finalMsg.isActive() && finalMsg.getEndDate().after(new Timestamp(System.currentTimeMillis())))
            msgs.add(finalMsg);
        return msgs;
    }

    /**
     * Gets all by board.
     *
     * @param board the board
     * @return the all by board
     */
    public List<Message> getAllByBoard(Board board) {
        return boardMessages.get(board.getId());
    }


    /**
     * The type Cleaner thread.
     */
    class CleanerThread extends Thread {
        @Override
        public void run() {
            while (true) {
                clean();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //todo: test
        private void clean() {
            for(Map.Entry entry : boardMessages.entrySet()){
                List<Message> messages = (List<Message>) entry.getValue();
                if(messages.removeIf(m -> m.getEndDate().before(new Timestamp(System.currentTimeMillis()))))
                    sendToBoard((Long) entry.getKey(), messages);
            }

        }
    }
}
