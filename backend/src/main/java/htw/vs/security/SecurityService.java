package htw.vs.security;

import org.springframework.stereotype.Component;

@Component("securityService")
public class SecurityService {
    public boolean hasPermission(String key) {
        return true;
    }
}