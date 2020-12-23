package htw.vs.rest;

import htw.vs.data.Group;
import htw.vs.data.GroupRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Group rest controller.
 */
@Api(tags = {SpringFoxConfig.GROUP})
@RestController
@RequestMapping(path = "/groups")
public class GroupRestController {
    private GroupRepository groupRepository;

    /**
     * Instantiates a new Group rest controller.
     *
     * @param groupRepository the group repository
     */
    public GroupRestController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
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
     * @param newGroup the new group
     * @return the response entity
     */
    @Operation(summary = "Add a new group")
    @PostMapping("/")
    public ResponseEntity addGroup(@RequestBody Group newGroup) {
        return new ResponseEntity<>(groupRepository.save(newGroup), HttpStatus.OK);
    }

    /**
     * Add user to group response entity.
     *
     * @param user the user
     * @param id   the id
     * @return the response entity
     */
    /**@Operation(summary = "Add user to group")
    @PostMapping("/{id}")
    public ResponseEntity addUserToGroup(@RequestBody User user, @PathVariable Long id) {
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);
        Group g;
        if(group.isPresent()){
            Group temp = group.get();
            temp.addUser(user);
            g = groupRepository.save(temp);
            response = new ResponseEntity(g, HttpStatus.OK);
        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group found");
        }
        return response;
    } **/

    /**
     * Add user to group response entity.
     *
     * @param user the user
     * @param id   the id
     * @return the response entity
     */
    /**@Operation(summary = "Set Coordinator")
    @PostMapping("/{id}")
    public ResponseEntity setCoordinator(@RequestBody User user, @PathVariable Long id) {
        ResponseEntity response;
        Optional<Group> group = groupRepository.findById(id);
        Group g;
        if(group.isPresent()){
            Group temp = group.get();
            temp.setCoordinator(user);
            g = groupRepository.save(temp);
            response = new ResponseEntity(g, HttpStatus.OK);
        }else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("No group found");
        }
        return response;
    }**/

    /**
     * Delete group response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Delete a group")
    //@Secured("USER_SUPERVISOR")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteGroup(@PathVariable Long id) {
        groupRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
