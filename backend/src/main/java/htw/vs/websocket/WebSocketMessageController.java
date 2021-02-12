package htw.vs.websocket;

import htw.vs.base.CONFIG;
import htw.vs.data.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import java.util.List;


//TODO: davide put exception texts in constants maybe make own exceptiones class
// pls do this davide :(

@Controller
public class WebSocketMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageManager messageManager;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public WebSocketMessageController(SimpMessagingTemplate simpMessagingTemplate, MessageManager messageManager, BoardRepository boardRepository, UserRepository userRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageManager = messageManager;
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    private void verifyUserBoard(Authentication authentication, User user, Board board) {

        verifyUser(authentication, user);

        if(!user.getGroups().stream().filter(g -> g.getBoard().getId() == board.getId()).findAny().isPresent())
            throw new AccessDeniedException("User is not assigned to this board");
    }

    private void verifyUser(Authentication authentication, User user) {
        //TODO:verify if user is coordinator/supervisor and allow him to do everything he wants bc he is the boss
        if (!user.getUserName().equals(authentication.getName()))
            throw new AccessDeniedException("UserName and authentication do not match");
    }


    @MessageMapping("/getActiveMessages")
    public boolean getActiveMessages(Authentication authentication, @Payload User user, @Payload Board board) {

        verifyUserBoard(authentication, user, board);

        List<Message> messages = messageManager.getAllByBoard(board);


        simpMessagingTemplate.convertAndSend(
                CONFIG.BASIC_TOPIC + board.getId(),
                messages);

        return true;
    }


    //used for sending new message or editing a message
    @MessageMapping("/message")
    public boolean message(Authentication authentication,  @Payload Message message) {

        Board board = message.getBoard();

        User user = userRepository.findById(message.getUser().getId())
                .orElseThrow(() -> new AccessDeniedException("User not found"));

        verifyUserBoard(authentication, user, board);

        List<Message> messages = messageManager.addOrReplace(message);

        simpMessagingTemplate.convertAndSend(CONFIG.BASIC_TOPIC + board.getId(),
                messages);

        return true;
    }

    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @MessageMapping("/pushMessage")
    public boolean pushMessageToCentral(Authentication authentication, @Payload Message message) {
        Message centralMsg = new Message(message);
        centralMsg.setId(null);
        Board centralBoard = boardRepository.findBoardByBoardName(CONFIG.CENTRAL_BOARD_NAME);

        if(message.getBoard().getId() == centralBoard.getId())
            throw new IllegalArgumentException("Referenced Board is not the central Board");

        verifyUser(authentication, message.getUser());

        List<Message> messages = messageManager.addOrReplace(centralMsg);


        simpMessagingTemplate.convertAndSend(
                CONFIG.BASIC_TOPIC + CONFIG.CENTRAL_BOARD_ID,
                messages);

        return true;
    }
}