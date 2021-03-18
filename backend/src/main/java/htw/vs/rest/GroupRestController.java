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
import java.util.stream.Collectors;

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
    @CrossOrigin(origins = "http://localhost")
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
    @CrossOrigin(origins = "http://localhost")
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
     * @param group the group
     * @return the response entity
     */
    @Operation(summary = "Add a new group and a new board")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/")
    public ResponseEntity addGroup(@RequestBody Group group) {
        ResponseEntity response;
        Group g;
        Optional<User> user = userRepository.findById(group.getCoordinator().getId());
        if(!user.isPresent())
        {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
        }

        else {
            Board newBoard = new Board();
            newBoard.setBoardName(group.getBoard().getBoardName());
            newBoard.setLocation(group.getBoard().getLocation());
            newBoard = boardRepository.save(newBoard);


            Group newGgroup = new Group();
            newGgroup.setGroupName(group.getGroupName());
            newGgroup.setBoard(newBoard);
            newGgroup.setCoordinator(user.get());
            newGgroup.getUsers().add(user.get());
            Role userRole = roleRepository.findByName(Const.COORDINATOR_ROLE);
            user.get().getRoles().add(userRole);
            userRole.getUsers().add(user.get());
            g = groupRepository.save(newGgroup);
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
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Add user to group")
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermissionGroup(authentication, #id)")
    @PostMapping("/user/{id}")
    public ResponseEntity addUserToGroup(@RequestBody Long userId, @PathVariable Long id) {
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
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Remove user from group")
    @Secured({"ROLE_SUPERVISOR", "ROLE_COORDINATOR"})
    @PreAuthorize("@securityService.hasPermissionGroup(authentication, #id)")
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

            if(!user.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
            }
            else {
                temp = changeCoordinatorRole(user.get(), temp);
                g = groupRepository.save(temp);

                response = new ResponseEntity(g, HttpStatus.OK);
            }
        }
        return response;
    }


    /**
     * Replace group response entity.
     *
     * @param id       the id
     * @param newGroup the new group
     * @return the response entity
     */
    @Operation
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/{id}")
    public ResponseEntity replaceGroup(@PathVariable Long id, @RequestBody Group newGroup){
        ResponseEntity response;
        Group g;
        Optional<Board> board;
        Optional<User>  coordidnator;

        Optional<Group> group = groupRepository.findById(id);
        if(newGroup.getBoard() != null){
            board = boardRepository.findById(newGroup.getBoard().getId());
        }else {
            board = null;
        }
        if(newGroup.getCoordinator() != null){
           coordidnator = userRepository.findById(newGroup.getCoordinator().getId());
        } else {
            coordidnator = null;
        }
        if(group.isPresent()) {
            Group temp = group.get();
            if(newGroup.getGroupName() != null){
                temp.setGroupName(newGroup.getGroupName());
            }

            if(!board.isPresent()){
                response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.NO_BOARD_MSG);
            }
            else {
                temp.setBoard(board.get());

                if(!coordidnator.isPresent()){
                    response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);
                }
                else {
                    temp = changeCoordinatorRole(coordidnator.get(), temp);
                    g = groupRepository.save(temp);

                    response = new ResponseEntity(g, HttpStatus.OK);
                }
            }


        }else {
            newGroup.setId(id);
            newGroup.setBoard(board.get());
            newGroup = changeCoordinatorRole(coordidnator.get(), newGroup);

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
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Delete a group")
    @Secured("ROLE_SUPERVISOR")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        Optional<Group> group = groupRepository.findById(id);
        User coordinator = group.get().getCoordinator();
        List<Group> coordinatedGroups = groupRepository.getCoordinatedGroups(coordinator);
        coordinatedGroups.removeIf(g -> g.getId() == group.get().getId());
        if(coordinatedGroups.size() == 0)
            coordinator.getRoles().removeIf(r -> r.getName().equals(Const.COORDINATOR_ROLE));

        group.get().setCoordinator(null);
        group.get().getUsers().forEach(u -> u.setGroups(u.getGroups().stream().filter(g -> g.getId() != id).collect(Collectors.toSet())));
        group.get().setUsers(null);
        group.get().setBoard(null);


        groupRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private Group changeCoordinatorRole(User newCoordinator, Group group){
        User oldCoordinator = group.getCoordinator();
        List<Group> groups = groupRepository.getCoordinatedGroups(oldCoordinator);
        Role coordinatorRole = roleRepository.findByName(Const.COORDINATOR_ROLE);
        if(groups.size() < 2) {
            oldCoordinator.getRoles().remove(coordinatorRole);
            coordinatorRole.getUsers().remove(oldCoordinator);
        }
        newCoordinator.getRoles().add(coordinatorRole);
        coordinatorRole.getUsers().add(newCoordinator);
        group.setCoordinator(newCoordinator);
        group.getUsers().add(newCoordinator);
        return group;
    }
}
