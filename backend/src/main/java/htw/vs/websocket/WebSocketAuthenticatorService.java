package htw.vs.websocket;

import htw.vs.data.User;
import htw.vs.data.UserRepository;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class WebSocketAuthenticatorService {

    private final UserRepository userRepository;

    public WebSocketAuthenticatorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UsernamePasswordAuthenticationToken getAuthenticatedOrFail(final String  username, final String password) throws AuthenticationException {
        if (username == null || username.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Username was null or empty.");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new AuthenticationCredentialsNotFoundException("Password was null or empty.");
        }
        User user = userRepository.findUserByUserName(username);

        if (user == null || user.getPassword() != password) {
            throw new BadCredentialsException("Bad credentials for user " + username);
        }

        // null credentials, we do not pass the password along
        List<GrantedAuthority> roles = new ArrayList<>();
        user.getRoles().forEach(r -> roles.add((GrantedAuthority) () -> r.getName()));
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                roles

        );
    }
}
