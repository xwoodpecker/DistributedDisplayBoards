package htw.vs.rest;

import htw.vs.data.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The type Group rest controller.
 */
@Api(tags = {SpringFoxConfig.GROUP})
@RestController
@RequestMapping(path = "/groups")
public class GroupRestController {
    private GroupRepository groupRepository;
    private UserRepository userRepository;
    private BoardRepository boardRepository;
    private RoleRepository roleRepository;

    /**
     * Instantiates a new Group rest controller.
     *  @param groupRepository the group repository
     * @param userRepository  the user repository
     * @param boardRepository the board repository
     * @param roleRepository
     */
    public GroupRestController(GroupRepository groupRepository, UserRepository userRepository, BoardRepository boardRepository, RoleRepository roleRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Gets groups.
     *
     * @return the groups
     */
    @Operation(summary = "Get all groups")
    @GetMapping(path = "/")
    public ResponseEntity<List<Group>> getGroups() {
        return new ResponseEntity<>(groupRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Gets group.
     *
     * @param id the id
     * @return the group
     */
    @Operation(summary = "Get group by given id")
    @GetMapping("/{id}")
    public ResponseEntity getGroup(@PathVariable Long id) {
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);

        if(group.isPresent())
            response = new ResponseEntity(group.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group found");

        return response;
    }


    /**
     * Add group response entity.
     *
     * @param groupName     the group name
     * @param boardId       the board id
     * @param coordinatorId the coordinator id
     * @return the response entity
     */
    @Operation(summary = "Add a new group")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/")
    public ResponseEntity addGroup(@RequestParam String groupName, @RequestParam Long boardId, @RequestParam Long coordinatorId) {
        ResponseEntity response;
        Group g;

        Optional<Board> board = boardRepository.findById(boardId);
        Optional<User> user = userRepository.findById(coordinatorId);
        if(!board.isPresent()){
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
        }
        else if(!user.isPresent())
        {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        else {
            Group group = new Group();
            group.setGroupName(groupName);
            group.setBoard(board.get());
            group.setCoordinator(user.get());
            Role userRole = roleRepository.findByName("COORDINATOR");
            user.get().getRoles().add(userRole);
            userRole.getUsers().add(user.get());
            g = groupRepository.save(group);
            response = new ResponseEntity(g, HttpStatus.OK);
        }

        return response;
    }


    /**
     * Add user to group response entity.
     *
     * @param userId the user id
     * @param id     the id
     * @return the response entity
     */
    @Operation(summary = "Add user to group")
    @Secured({"USER_SUPERVISOR", "ROLE_COORDINATOR"})
    @PostMapping("/user/{id}")
    public ResponseEntity addUserToGroup(@RequestParam Long userId, @PathVariable Long id) {
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);
        Group g;

        if(group.isPresent()){
            Group temp = group.get();

            Optional<User> user = userRepository.findById(userId);
            if(!user.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
            else {
                temp.getUsers().add(user.get());
                g = groupRepository.save(temp);
                response = new ResponseEntity(g, HttpStatus.OK);
            }
        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group found");
        }
        return response;
    }

    @Operation(summary = "Change Coordinator")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/coordinator/{id}")
    public ResponseEntity changeCoordinator(@RequestParam Long newCoordinatorId, @PathVariable Long id){
        ResponseEntity response;
        Group g;

        Optional<Group> group = groupRepository.findById(id);
        Optional<User> user = userRepository.findById(newCoordinatorId);

        if(!group.isPresent()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group found");
        }
        else if(!user.isPresent()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        else {
            Group temp = group.get();
            User oldCoordinator = temp.getCoordinator();
            //TODO Coordinator in anderer Gruppe?
            Role coordinatorRole = roleRepository.findByName("COORDINATOR");

            oldCoordinator.getRoles().remove(coordinatorRole);
            coordinatorRole.getUsers().remove(oldCoordinator);

            User newCoordinator = user.get();
            newCoordinator.getRoles().add(coordinatorRole);
            coordinatorRole.getUsers().add(newCoordinator);
            temp.setCoordinator(newCoordinator);

            g = groupRepository.save(temp);

            response = new ResponseEntity(g, HttpStatus.OK);
        }
        return response;
    }

    /**
     * Delete group response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Delete a group")
    @Secured("ROLE_SUPERVISOR")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        group.get().setCoordinator(null);

        groupRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
