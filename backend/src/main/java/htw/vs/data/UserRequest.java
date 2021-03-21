package htw.vs.data;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User.
 */

public class UserRequest {

    public String email;
    public boolean isSupervisor;
    public String userName;
    public String password;
    public boolean isEnabled;
    /**
     * Instantiates a new User.
     */
    public UserRequest(String email, boolean isSupervisor, String userName, String password, boolean isEnabled) {
            this.email = email;
            this.isSupervisor = isSupervisor;
            this.userName = userName;
            this.password = password;
            this.isEnabled = isEnabled;
    }

}
