package htw.vs.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.Set;
import java.util.stream.Collectors;

@Component("securityService")
public class SecurityService {
    public boolean hasPermission(String key) {
        return true;
    }

    public boolean hasPermission(Authentication authentication, String foo) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

        Principal principal = (Principal) authentication.getPrincipal();
        String name = principal.getName();

        if(roles.contains("ROLE_COORDINATOR"))
            return true;

        return true;
    }
}