package htw.vs.data;


import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Cascade;

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

    @Column(name = "username", unique = true, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "USERS_ROLES")
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "users", fetch=FetchType.EAGER)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<Group> groups  = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Message> messages = new HashSet<>();

    /**
     * Instantiates a new User.
     */
    public User() {

    }

    /**
     * Instantiates a new User.
     *
     * @param id the id
     */
    public User(Long id) {
        this.id = id;
    }

    /**
     * Instantiates a new User.
     *
     * @param id       the id
     * @param userName the user name
     * @param password the password
     * @param enabled  the enabled
     * @param email    the email
     * @param roles    the roles
     * @param groups   the groups
     */
    public User(Long id, String userName, String password, boolean enabled, String email, Set<Role> roles, Set<Group> groups, Set<Message> messages) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.enabled = enabled;
        this.email = email;
        this.roles = roles;
        this.groups = groups;
        this.messages = messages;
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
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets user name.
     *
     * @return the user name
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

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }


    /** for principal
    @Override
    public String getName() {
        return userName;
    }**/

}
