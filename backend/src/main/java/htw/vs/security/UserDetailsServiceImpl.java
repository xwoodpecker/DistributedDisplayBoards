package htw.vs.security;

import htw.vs.data.User;
import htw.vs.data.UserRepository;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The type User details service.
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    /**
     * Instantiates a new User details service.
     *
     * @param userRepository the user repository
     */
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUserName(username);
        UserBuilder builder = null;
        if (user != null){
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.disabled(!user.isEnabled());
            builder.password(user.getPassword());
            String[] roles = user.getRoles().stream().map(a -> a.getName()).toArray(String[]::new);
            builder.roles(roles);
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
        return builder.build();
    }
}
