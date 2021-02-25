package htw.vs.websocket;

import htw.vs.base.CONST;
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

@Controller
public class WebSocketMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;


    private Map<Long, List<Message>> boardMessages;


    void initialize() {
        new CleanerThread().start();
    }

    public WebSocketMessageController(SimpMessagingTemplate simpMessagingTemplate, BoardRepository boardRepository, UserRepository userRepository, MessageRepository messageRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;


        generateBoardMessages();
        initialize();
    }


    private void verifyUser(Authentication authentication, User user) {
        //TODO:verify if user is coordinator/supervisor and allow him to do everything he wants bc he is the boss
        if (!user.getUserName().equals(authentication.getName()))
            throw new AccessDeniedException(CONST.VERIFY_USER_EXCEPTION);
    }


    @MessageMapping("/getActiveMessages")
    public boolean getActiveMessages(Authentication authentication, @Payload User user, @Payload Board board) {

        verifyUserBoard(authentication, user, board);

        List<Message> messages = getAllByBoard(board);

        sendToBoard(board.getId(),messages);

        return true;
    }


    //used for sending new message or editing a message
    @MessageMapping("/message")
    public boolean message(Authentication authentication,  @Payload Message message) {

        Board board = message.getBoard();

        User user = userRepository.findById(message.getUser().getId())
                .orElseThrow(() -> new AccessDeniedException(CONST.USER_NOT_FOUND_EXCEPTION));

        verifyUserBoard(authentication, user, board);

        List<Message> messages = addOrReplace(message);

        sendToBoard(board.getId(),messages);

        return true;
    }

    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @MessageMapping("/pushMessage")
    public boolean pushMessageToCentral(Authentication authentication, @Payload Message message) {
        Message centralMsg = new Message(message);
        centralMsg.setId(null);
        Board centralBoard = boardRepository.findBoardByBoardName(CONST.CENTRAL_BOARD_NAME);

        if(message.getBoard().getId() == centralBoard.getId())
            throw new IllegalArgumentException(CONST.PUSH_MESSAGE_EXCEPTION);

        verifyUser(authentication, message.getUser());

        List<Message> messages = addOrReplace(centralMsg);

        sendToBoard(centralBoard.getId(),messages);

        return true;
    }


    private void verifyUserBoard(Authentication authentication, User user, Board board) {

        verifyUser(authentication, user);

        if(!user.getGroups().stream().filter(g -> g.getBoard().getId() == board.getId()).findAny().isPresent())
            throw new AccessDeniedException(CONST.USER_BOARD_EXCEPTION);
    }


    public void sendToBoard(Long boardId, List<Message> messages) {
        simpMessagingTemplate.convertAndSend(
                CONST.BASIC_TOPIC + boardId,
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


    public List<Message> addOrReplace(Message msg) {
        msg = messageRepository.save(msg);
        List<Message> msgs = boardMessages.get(msg.getBoard());
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

    public List<Message> getAllByBoard(Board board) {
        return boardMessages.get(board);
    }


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