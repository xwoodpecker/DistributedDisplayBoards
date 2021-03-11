package htw.vs.base;

import htw.vs.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Setup data loader.
 */
@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * The Already setup.
     */
    boolean alreadySetup = false;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final BoardRepository boardRepository;

    /**
     * Instantiates a new Setup data loader.
     *
     * @param roleRepository  the role repository
     * @param userRepository  the user repository
     * @param boardRepository the board repository
     */
    public SetupDataLoader(RoleRepository roleRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
        return;
        createRoleIfNotFound(Const.SUPERVISOR_ROLE);
        createRoleIfNotFound(Const.COORDINATOR_ROLE);
        createRoleIfNotFound(Const.USER_ROLE);
        Role supervisorRole = roleRepository.findByName(Const.SUPERVISOR_ROLE);
        Role coordinatorRole = roleRepository.findByName(Const.COORDINATOR_ROLE);
        Role userRole = roleRepository.findByName(Const.USER_ROLE);
        createUserIfNotFound("supervisor", "", Config.DEFAULT_SUPERVISOR_PASSWORD, new HashSet<>(Arrays.asList(supervisorRole, coordinatorRole, userRole)));
        createBoardIfNotFound(Config.CENTRAL_BOARD_NAME);
        alreadySetup = true;
    }

    /**
     * Create role if not found role.
     *
     * @param name the name
     * @return the role
     */
    @Transactional
    Role createRoleIfNotFound(final String name){

        Role role = roleRepository.findByName(name);
        if(role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

    /**
     * Create user if not found user.
     *
     * @param username the username
     * @param email    the email
     * @param password the password
     * @param roles    the roles
     * @return the user
     */
    @Transactional
    User createUserIfNotFound(String username, String email, String password, Set<Role> roles){
        User user = userRepository.findUserByUserName(username);
        if(user == null){
            user = new User();
            user.setUserName(username);
            user.setEmail(email);
            user.setPassword(passwordEncoder().encode(password));
            user.setEnabled(true);
        }
        user.setRoles(roles);
        user = userRepository.save(user);
        User finalUser = user;
        user.getRoles().forEach(role -> role.getUsers().add(finalUser));
        return user;
    }

    /**
     * Create board if not found board.
     *
     * @param boardName the board name
     * @return the board
     */
    @Transactional
    Board createBoardIfNotFound(String boardName){
        Board board = boardRepository.findBoardByBoardName(boardName);
        if(board == null){
            board = new Board();
            board.setBoardName(boardName);
        }
        board = boardRepository.save(board);
        return board;
    }
}
