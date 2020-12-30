package htw.vs.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Role.
 */
@Entity
@Table(name = "roles")
@JsonIgnoreProperties(value = "users")
public class Role {
    @Id
    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "USERS_ROLES")
    private Set<User> users = new HashSet<>();

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
