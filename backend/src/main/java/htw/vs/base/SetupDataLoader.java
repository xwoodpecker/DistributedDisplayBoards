package htw.vs.base;

import htw.vs.data.Role;
import htw.vs.data.RoleRepository;
import htw.vs.data.User;
import htw.vs.data.UserRepository;
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

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;
    private final String INITIAL_ADMIN_PASSWORD = CONFIG.DEFAULT_ADMIN_PASSWORD;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup)
            return;
        createRoleIfNotFound("SUPERVISOR");
        createRoleIfNotFound("COORDINATOR");
        createRoleIfNotFound("USER");
        Role supervisorRole = roleRepository.findByName("SUPERVISOR");
        createUserIfNotfound("supervisor", "", INITIAL_ADMIN_PASSWORD, new HashSet<>(Arrays.asList(supervisorRole)));
        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(final String name){

        Role role = roleRepository.findByName(name);
        if(role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    User createUserIfNotfound(String username, String email, String password, Set<Role> roles){
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
}
