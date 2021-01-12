package htw.vs.data;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The type User.
 */
@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "email")
    private String email;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "users")
    private Set<Group> groups  = new HashSet<>();

    /**
     * Default constructor for JPA only.
     */
    public User() {

    }

    public User(Long id) {
        this.id = id;
    }

    public User(Long id, String userName, String password, boolean enabled, String email, Set<Role> roles, Set<Group> groups) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.roles = roles;
        this.groups = groups;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set JPA id - for testing and JPA only. Not intended for normal use.
     *
     * @param id The new id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets userName.
     *
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Is enabled boolean.
     *
     * @return the boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets enabled.
     *
     * @param enabled the enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets roles.
     *
     * @return the roles
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Sets roles.
     *
     * @param roles the roles
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Gets groups.
     *
     * @return the groups
     */
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * Sets groups.
     *
     * @param groups the groups
     */
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return userName;
    }


    /** for principal
    @Override
    public String getName() {
        return userName;
    }**/

}
