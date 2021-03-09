package htw.vs.rest;

import htw.vs.base.CONST;
import htw.vs.data.Role;
import htw.vs.data.RoleRepository;
import htw.vs.data.User;
import htw.vs.data.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The type User rest controller.
 */
@Api(tags = {SpringFoxConfig.USER})
@RestController
@RequestMapping(path = "/users")
public class UserRestController {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    /**
     * Instantiates a new User rest controller.
     *
     * @param userRepository  the user repository
     * @param passwordEncoder the password encoder
     * @param roleRepository  the role repository
     */
    public UserRestController(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Get users response entity.
     *
     * @return the response entity
     */
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
    @Operation(summary = "Get user with given id")
    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable Long id) {
        ResponseEntity response;
        Optional<User> user = userRepository.findById(id);

        if(user.isPresent())
            response = new ResponseEntity<>(user.get(), HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(CONST.USER_NOT_FOUND_MSG);

        return response;
    }

    /**
     * Gets authenticated user (HTTP authentication)
     *
     * @return the user
     */
    @Operation(summary = "Get user that is authenticated")
    @GetMapping("/login")
    public ResponseEntity getUserByLoginCredentials(UsernamePasswordAuthenticationToken principal){
        ResponseEntity response;

        UserDetails userDetails = (UserDetails) principal.getPrincipal();
        User user = userRepository.findUserByUserName(userDetails.getUsername());

        if(user != null)
            response = new ResponseEntity<>(user, HttpStatus.OK);
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(CONST.USER_NOT_FOUND_MSG);
        return response;
    }

    /**
     * Add user response entity.
     *
     * @param userName     the user name
     * @param password     the password
     * @param email        the email
     * @param isSupervisor the is supervisor
     * @return the response entity
     */
    @Operation(summary = "Add a new user")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/")
    public ResponseEntity addUser(@RequestParam String userName, @RequestParam String password, @RequestParam String email, @RequestParam boolean isSupervisor){
        User user = new User();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setEmail(email);
        Role userRole = roleRepository.findByName(CONST.USER_ROLE);
        user.getRoles().add(userRole);
        userRole.getUsers().add(user);
        if(isSupervisor){
            Role superVisorRole = roleRepository.findByName(CONST.SUPERVISOR_ROLE);
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
    @Operation(summary = "Change the password of a user")
    @PostMapping("/password/own")
    public ResponseEntity changeOwnPassword(UsernamePasswordAuthenticationToken principal, @RequestParam String newPassword){
        UserDetails userDetails = (UserDetails) principal.getPrincipal();
        User user = userRepository.findUserByUserName(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    //todo: test
    /**
     * Change someones password response entity.
     *
     * @param username    the username
     * @param newPassword the new password
     * @return the response entity
     */
    @Operation(summary = "Change password of a specified user. Supervisor only")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/password/other")
    public ResponseEntity changeSomeonesPassword(String username, String newPassword){
        User user = userRepository.findUserByUserName(username);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Replace user response entity.
     *
     * @param newUser the new user
     * @param id      the id
     * @return the response entity
     */
    @Operation(summary = "Replace a user with a new user")
    @Secured("ROLE_SUPERVISOR")
    @PostMapping("/{id}")
    public ResponseEntity replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        User u;
        if(user.isPresent()){
            User temp = user.get();
            temp.setUserName(newUser.getUserName());
            temp.setPassword(passwordEncoder.encode(newUser.getPassword()));
            temp.setEmail(newUser.getEmail());
            temp.setEnabled(newUser.isEnabled());
            temp.setRoles(newUser.getRoles());
            u = userRepository.save(temp);
        }else{
            newUser.setId(id);
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
    @Operation(summary = "Delete a user")
    @Secured("ROLE_SUPERVISOR")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    /**
     * Get boards of user response entity.
     *
     * @param id the id
     * @return the response entity
     */
    @Operation(summary = "Get Boards of a user")
    @Secured("ROLE_USER")
    @GetMapping("/{id}/boards")
    public ResponseEntity getBoardsOfUser(@PathVariable Long id){
        return new ResponseEntity<>(userRepository.findById(id).get().getGroups(), HttpStatus.OK);
    }

}
