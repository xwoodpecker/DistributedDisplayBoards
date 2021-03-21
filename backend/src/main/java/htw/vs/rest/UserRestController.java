package htw.vs.rest;

import htw.vs.base.Config;
import htw.vs.base.Const;
import htw.vs.data.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type User rest controller.
 */
@Api(tags = {SpringFoxConfig.USER})
@RestController
@RequestMapping(path = "/api/users")
public class UserRestController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private GroupRepository groupRepository;

    /**
     * Instantiates a new User rest controller.
     *
     * @param userRepository  the user repository
     * @param passwordEncoder the password encoder
     * @param roleRepository  the role repository
     * @param groupRepository the group repository
     */
    public UserRestController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, GroupRepository groupRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * Get users response entity.
     *
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get all users")
    @GetMapping("/")
    public ResponseEntity getUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Gets user.
     *
     * @param id the id
     * @return the user
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get user with given id")
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        ResponseEntity response;
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent())
            response = new ResponseEntity<>(user.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);

        return response;
    }

    /**
     * Gets authenticated user (HTTP authentication)
     *
     * @param principal the principal
     * @return the user
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get user that is authenticated")
    @GetMapping("/login")
    public ResponseEntity getUserByLoginCredentials(UsernamePasswordAuthenticationToken principal){
        ResponseEntity response;

        UserDetails userDetails = (UserDetails) principal.getPrincipal();
        User user = userRepository.findUserByUserName(userDetails.getUsername());

        if(user != null)
            response = new ResponseEntity<>(user, HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(Const.USER_NOT_FOUND_MSG);

        return response;
    }

    /**
     * Add user response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @CrossOrigin(origins = "*")
    @Operation(summary = "Add a new user")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/")
    public ResponseEntity addUser(@RequestBody UserRequest request){
        User user = new User();
        user.setUserName(request.userName);
        user.setPassword(passwordEncoder.encode(request.password));
        user.setEnabled(true);
        user.setEmail(request.email);
        Role userRole = roleRepository.findByName(Const.USER_ROLE);
        user.getRoles().add(userRole);
        userRole.getUsers().add(user);
        if(request.isSupervisor){
            Role superVisorRole = roleRepository.findByName(Const.SUPERVISOR_ROLE);
            user.getRoles().add(superVisorRole);
            superVisorRole.getUsers().add(user);
        }
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    /**
     * Change own password response entity.
     *
     * @param principal   the principal
     * @param newPassword the new password
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Change the password of a user")
    @PostMapping("/password/own")
    public ResponseEntity changeOwnPassword(UsernamePasswordAuthenticationToken principal, @RequestBody String newPassword){
        UserDetails userDetails = (UserDetails) principal.getPrincipal();
        User user = userRepository.findUserByUserName(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Replace user response entity.
     *
     * @param authentication the authentication
     * @param userRequest    the user request
     * @param id             the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Replace a user with a new user")
    @PostAuthorize("@securityService.hasPermissionUser(authentication, #id)")
    @PostMapping("/{id}")
    public ResponseEntity replaceUser(Authentication authentication, @RequestBody UserRequest userRequest, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        User u;
        if(user.isPresent()){
            User temp = user.get();
            if(userRequest.userName != null && !userRequest.userName.isEmpty()){
                temp.setUserName(userRequest.userName);
            }
            if( userRequest.password != null && !userRequest.password.isEmpty()){
                temp.setPassword(passwordEncoder.encode(userRequest.password));
            }
            if( userRequest.email != null && !userRequest.email.isEmpty()){
                temp.setEmail(userRequest.email);
            }
            Role superVisorRole = roleRepository.findByName(Const.SUPERVISOR_ROLE);
            if(userRequest.isSupervisor){
                temp.getRoles().add(superVisorRole);
                superVisorRole.getUsers().add(temp);
            }
            else{
                temp.getRoles().remove(superVisorRole);
                superVisorRole.getUsers().remove(temp);
            }
            temp.setEnabled(true);


            u = userRepository.save(temp);
        }else{
            User newUser = new User();
            newUser.setUserName(userRequest.userName);
            newUser.setPassword(passwordEncoder.encode(userRequest.password));
            newUser.setEnabled(true);
            newUser.setEmail(userRequest.email);
            Role userRole = roleRepository.findByName(Const.USER_ROLE);
            newUser.getRoles().add(userRole);
            userRole.getUsers().add(newUser);
            u = userRepository.save(newUser);
        }
        return new ResponseEntity<>(u, HttpStatus.OK);
    }


    /**
     * Delete user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "*")
    @Operation(summary = "Delete a user")
    @Secured("ROLE_SUPERVISOR")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        user.get().getGroups().forEach(g -> g.setUsers(g.getUsers().stream().filter(u -> u.getId() != id).collect(Collectors.toSet())));
        user.get().setGroups(null);
        user.get().setRoles(null);
        userRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Get groups of user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @CrossOrigin(origins = "http://localhost")
    @Operation(summary = "Get Groups of a user")
    @GetMapping("/{id}/groups")
    public ResponseEntity getGroupsOfUser(@PathVariable Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new AccessDeniedException(Const.USER_NOT_FOUND_EXCEPTION));
        if(user.getRoles().stream().filter(r -> r.getName().equals(Const.SUPERVISOR_ROLE)).count() > 0)
            return new ResponseEntity<>(groupRepository.findAll(), HttpStatus.OK);

        Group central = groupRepository.findGroupByGroupName(Config.CENTRAL_GROUP_NAME);
        List<Group> groups = userRepository.findById(id).get().getGroups().stream().collect(Collectors.toList());
        groups.add(central);

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }
}
