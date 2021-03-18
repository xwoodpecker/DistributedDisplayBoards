package htw.vs.rest;

import htw.vs.base.Const;
import htw.vs.data.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

/**
 * The type Message rest controller.
 */
@Api(tags = {SpringFoxConfig.MESSAGES})
@RestController
@RequestMapping(path = "/messages")
public class MessageRestController {
    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private GroupRepository groupRepository;
    private BoardRepository boardRepository;

    /**
     * Instantiates a new Message rest controller.
     *
     * @param userRepository    the user repository
     * @param messageRepository the message repository
     * @param groupRepository   the group repository
     * @param boardRepository   the board repository
     */
    public MessageRestController(UserRepository userRepository, MessageRepository messageRepository, GroupRepository groupRepository, BoardRepository boardRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
        this.boardRepository = boardRepository;
    }

    /**
     * Get messages response entity.
     *
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all messages")
    @Secured("ROLE_SUPERVISOR")
    @GetMapping(path = "/")
    public ResponseEntity<List<Message>> getMessages(){
        return new ResponseEntity<>(messageRepository.findAll(), HttpStatus.OK);
    }


    /**
     * Gets message.
     *
     * @param id the id
     * @return the message
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get messsage by given id")
    @GetMapping("/{id}")
    public ResponseEntity getMessage(@PathVariable Long id) {
        ResponseEntity response;
        Optional<Message> message = messageRepository.findById(id);

        if (message.isPresent())
            response = new ResponseEntity(message.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Message not found");

        return response;
    }


    /**
     * Get messages of group response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all messages of a group")
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermissionGroup(authentication, #id)")
    @GetMapping(path = "/group/{id}")
    public ResponseEntity<List<Message>> getMessagesOfGroup(@PathVariable Long id){
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);

        if(group.isPresent()){
            Group temp = group.get();

            Optional<Board> board = boardRepository.findById(temp.getId());
            if(!board.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
            }
            else {
                response = new ResponseEntity(messageRepository.findMessagesByBoard(board.get()), HttpStatus.OK);
            }
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group found");
        }

        return response;
    }

    /**
     * Get messages of board response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all messages of a board")
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermissionBoard(authentication, #id)")
    @GetMapping(path = "/board/{id}")
    public ResponseEntity<List<Message>> getMessagesOfBoard(@PathVariable Long id){
        ResponseEntity response;
        Optional<Board> board = boardRepository.findById(id);

        if(!board.isPresent()){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
        }
        else {
            response = new ResponseEntity(messageRepository.findMessagesByBoard(board.get()), HttpStatus.OK);
        }

        return response;
    }

    /**
     * Get active messages of group response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all active messages of a group")
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermissionGroup(authentication, #id)")
    @GetMapping(path = "/active/group/{id}")
    public ResponseEntity<List<Message>> getActiveMessagesOfGroup(@PathVariable Long id){
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);

        if(group.isPresent()){
            Group temp = group.get();

            Optional<Board> board = boardRepository.findById(temp.getId());
            if(!board.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
            }
            else {
                response = new ResponseEntity(messageRepository.findActiveMessagesByBoard(board.get()), HttpStatus.OK);
            }
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group found");
        }

        return response;
    }

    /**
     * Get active messages of board response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all active messages of a board")
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermissionBoard(authentication, #id)")
    @GetMapping(path = "/active/board/{id}")
    public ResponseEntity<List<Message>> getActiveMessagesOfBoard(@PathVariable Long id){
        ResponseEntity response;
        Optional<Board> board = boardRepository.findById(id);

        if(!board.isPresent()){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
        }
        else {
            response = new ResponseEntity(messageRepository.findActiveMessagesByBoard(board.get()), HttpStatus.OK);
        }

        return response;
    }

    /**
     * Get messages of user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all messages of a User")
    @GetMapping(path = "/user/{id}")
    public ResponseEntity<List<Message>> getMessagesOfUser(@PathVariable Long id){
        ResponseEntity response;
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            response = new ResponseEntity(messageRepository.findMessagesByUser(user.get()), HttpStatus.OK);

        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return response;
    }

    /**
     * Get active message of user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all active messages of a User")
    @GetMapping(path = "/active/user/{id}")
    public ResponseEntity<List<Message>> getActiveMessageOfUser(@PathVariable Long id){
        ResponseEntity response;
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent()){
            response = new ResponseEntity(messageRepository.findActiveMessagesByUser(user.get()), HttpStatus.OK);

        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return response;
    }

    /**
     * Add message response entity.
     *
     * @param active      the active
     * @param boardId     the board id
     * @param userId      the user id
     * @param content     the content
     * @param displayTime the display time
     * @param endDate     the end date
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "create a new Message")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/")
    public ResponseEntity addMessage(@RequestParam Boolean active, @RequestParam Long boardId, @RequestParam Long userId, @RequestParam String content, @RequestParam Integer displayTime, @RequestParam Timestamp endDate){
        ResponseEntity response;
        Message m;

        Optional<Board> board = boardRepository.findById(boardId);
        Optional<User> user = userRepository.findById(userId);
        if(!board.isPresent()){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
        }
        else if(!user.isPresent())
        {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        else {
            Message message = new Message();
            message.setActive(active);
            message.setBoard(board.get());
            message.setUser(user.get());
            message.setContent(content);
            message.setDisplayTime(displayTime);
            message.setEndDate(endDate);
            m = messageRepository.save(message);
            response = new ResponseEntity(m, HttpStatus.OK);
        }

        return response;
    }

    /**
     * Delete message response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Delete a Message")
    @Secured("ROLE_SUPERVISOR")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteMessage(@PathVariable Long id) {

        messageRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
