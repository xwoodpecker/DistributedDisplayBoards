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
     *
     * @param groupRepository the group repository
     * @param userRepository  the user repository
     * @param boardRepository the board repository
     * @param roleRepository  the role repository
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
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.NO_GROUP_MSG);

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
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.BOARD_NOT_FOUND_MSG);
        }
        else if(!user.isPresent())
        {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
        }

        else {
            Group group = new Group();
            group.setGroupName(groupName);
            group.setBoard(board.get());
            group.setCoordinator(user.get());
            Role userRole = roleRepository.findByName(Const.COORDINATOR_ROLE);
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
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermission(authentication, #id)")
    @PostMapping("/user/{id}")
    public ResponseEntity addUserToGroup(@RequestParam Long userId, @PathVariable Long id) {
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);
        Group g;

        if(group.isPresent()){
            Group temp = group.get();

            Optional<User> user = userRepository.findById(userId);
            if(!user.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
            }
            else {
                temp.getUsers().add(user.get());
                g = groupRepository.save(temp);
                response = new ResponseEntity(g, HttpStatus.OK);
            }
        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.NO_GROUP_MSG);
        }
        return response;
    }

    /**
     * Delete user from group response entity.
     *
     * @param userId the user id
     * @param id     the id
     * @return the response entity
     */
    @Operation(summary = "Remove user from group")
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermission(authentication, #id)")
    @DeleteMapping("/user/{id}")
    public ResponseEntity deleteUserFromGroup(@RequestParam Long userId, @PathVariable Long id) {
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);
        Group g;

        if(group.isPresent()){
            Group temp = group.get();

            Optional<User> user = userRepository.findById(userId);
            if(!user.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
            }
            else {
                temp.getUsers().remove(user.get());
                g = groupRepository.save(temp);
                response = new ResponseEntity(g, HttpStatus.OK);
            }
        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.NO_GROUP_MSG);
        }
        return response;
    }

    /**
     * Change coordinator response entity.
     *
     * @param newCoordinatorId the new coordinator id
     * @param id               the id
     * @return the response entity
     */
    @Operation(summary = "Change Coordinator")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/coordinator/{id}")
    public ResponseEntity changeCoordinator(@RequestParam Long newCoordinatorId, @PathVariable Long id){
        ResponseEntity response;
        Group g;

        Optional<Group> group = groupRepository.findById(id);
        Optional<User> user = userRepository.findById(newCoordinatorId);

        if(!group.isPresent()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.NO_GROUP_MSG);
        }
        else if(!user.isPresent()) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
        }

        else {
            Group temp = group.get();
            User oldCoordinator = temp.getCoordinator();
            List<Group> groups = groupRepository.getCoordinatedGroups(oldCoordinator);

            Role coordinatorRole = roleRepository.findByName(Const.COORDINATOR_ROLE);

            //todo test
            if(groups.size() < 2) {
                oldCoordinator.getRoles().remove(coordinatorRole);
                coordinatorRole.getUsers().remove(oldCoordinator);
            }

            User newCoordinator = user.get();
            //todo test duplicate ?
            newCoordinator.getRoles().add(coordinatorRole);
            coordinatorRole.getUsers().add(newCoordinator);
            temp.setCoordinator(newCoordinator);

            g = groupRepository.save(temp);

            response = new ResponseEntity(g, HttpStatus.OK);
        }
        return response;
    }

    @Operation
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/{id}")
    public ResponseEntity replaceGroup(@PathVariable Long id, @RequestBody Group newGroup){
        ResponseEntity response;
        Group g;

        Optional<Group> group = groupRepository.findById(id);
        Optional<Board> board = boardRepository.findById(newGroup.getBoard().getId());
        Optional<User>  coordidnator = userRepository.findById(newGroup.getCoordinator().getId());
        if(group.isPresent()) {
            Group temp = group.get();
            temp.setGroupName(newGroup.getGroupName());

            if(!board.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.NO_BOARD_MSG);
            }
            else {
                temp.setBoard(newGroup.getBoard());

                if(!coordidnator.isPresent()){
                    response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
                }
                else {
                    User oldCoordinator = temp.getCoordinator();
                    List<Group> groups = groupRepository.getCoordinatedGroups(oldCoordinator);
                    Role coordinatorRole = roleRepository.findByName(Const.COORDINATOR_ROLE);

                    //todo test
                    if(groups.size() < 2) {
                        oldCoordinator.getRoles().remove(coordinatorRole);
                        coordinatorRole.getUsers().remove(oldCoordinator);
                    }
                    User newCoordinator = coordidnator.get();
                    //todo test duplicate ?
                    newCoordinator.getRoles().add(coordinatorRole);
                    coordinatorRole.getUsers().add(newCoordinator);
                    temp.setCoordinator(newCoordinator);

                    g = groupRepository.save(temp);

                    response = new ResponseEntity(g, HttpStatus.OK);
                }
            }


        }else {
            newGroup.setId(id);
            g = groupRepository.save(newGroup);
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
        group.get().setUsers(null);

        groupRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
