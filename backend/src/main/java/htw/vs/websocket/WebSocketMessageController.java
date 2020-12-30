package htw.vs.websocket;

import htw.vs.base.CONFIG;
import htw.vs.data.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class WebSocketMessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageManager messageManager;
    private final BoardRepository boardRepository;

    public WebSocketMessageController(SimpMessagingTemplate simpMessagingTemplate, MessageManager messageManager, BoardRepository boardRepository) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageManager = messageManager;
        this.boardRepository = boardRepository;
    }


    @MessageMapping("/topic/user/getActiveMessages")
    public boolean getActiveMessages(@Payload User user, @Payload Board board) {
        if(!user.getGroups().stream().filter(g -> g.getBoard().getId() == board.getId()).findAny().isPresent())
            //TODO
            return false;

        List<Message> messages = messageManager.getAllByBoard(board);


        simpMessagingTemplate.convertAndSend(
                CONFIG.BASIC_TOPIC + board.getBoardName(),
                messages);
        return true;
    }

    //used for sending new message or editing a message
    @MessageMapping("/topic/user/message")
    public boolean message(@Payload Message message) {
        Board board = message.getBoard();
        User user = message.getUser();
        if(!user.getGroups().stream().filter(g -> g.getBoard().getId() == board.getId()).findAny().isPresent()) //TODO
            return false;

        List<Message> messages = messageManager.addOrReplace(message);

        simpMessagingTemplate.convertAndSend(
                CONFIG.BASIC_TOPIC + message.getBoard().getBoardName(),
                messages);
        return true;
    }

    @Secured("hasRole('COORDINATOR')")
    @MessageMapping("coordinator/pushMessage")
    public void pushMessageToCentral(@Payload Message message) {
        Message centralMsg = new Message(message);
        centralMsg.setId(null);
        Board centralBoard = boardRepository.findBoardByBoardName(CONFIG.CENTRAL_BOARD_NAME);

        //TODO check if board = central board

        List<Message> messages = messageManager.addOrReplace(centralMsg);

        simpMessagingTemplate.convertAndSend(
                CONFIG.BASIC_TOPIC + CONFIG.CENTRAL_BOARD_NAME,
                messages);
    }
}