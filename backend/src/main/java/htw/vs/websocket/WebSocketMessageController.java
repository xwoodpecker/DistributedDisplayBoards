package htw.vs.websocket;

import htw.vs.base.CONFIG;
import htw.vs.data.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

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


    @MessageMapping("/getActiveMessages")
    public boolean getActiveMessages(@Payload User user, @Payload Board board) {
        if(!user.getGroups().stream().filter(g -> g.getBoard().getId() == board.getId()).findAny().isPresent())
            //TODO
            return false;

        List<Message> messages = messageManager.getAllByBoard(board);


        simpMessagingTemplate.convertAndSend(
                CONFIG.BASIC_TOPIC + board.getId(),
                messages);

        return true;
    }

    //used for sending new message or editing a message
    @MessageMapping("/message")
    public boolean message(Authentication authentication,  @Payload Message message) { //todo david

        Board board = message.getBoard();

        User user = userRepository.findById(message.getUser().getId()).orElseThrow(); //TODO


        if(!user.getUserName().equals(authentication.getName()))
            return false; //TODO EXCEPTIONES david

        if(!user.getGroups().stream().filter(g -> g.getBoard().getId() == board.getId()).findAny().isPresent()) //TODO david
            return false; //TODO EXCEPTIONES david

        List<Message> messages = messageManager.addOrReplace(message);

        simpMessagingTemplate.convertAndSend(CONFIG.BASIC_TOPIC + board.getId(),
                messages);

        return true;
    }

    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @MessageMapping("/coordinator/pushMessage")
    public boolean pushMessageToCentral(@Payload Message message) {
        Message centralMsg = new Message(message);
        centralMsg.setId(null);
        Board centralBoard = boardRepository.findBoardByBoardName(CONFIG.CENTRAL_BOARD_NAME);

        if(message.getBoard().getId() == centralBoard.getId())
            return false;

        List<Message> messages = messageManager.addOrReplace(centralMsg);

        simpMessagingTemplate.convertAndSend(
                CONFIG.BASIC_TOPIC + CONFIG.CENTRAL_BOARD_ID,
                messages);

        return true;
    }
}