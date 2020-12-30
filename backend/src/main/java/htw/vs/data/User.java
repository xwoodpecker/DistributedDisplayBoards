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

    @OneToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "USERS_BOARDS")
    private Set<Board> boards  = new HashSet<>();

    /**
     * Default constructor for JPA only.
     */
    public User() {

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * Gets boards.
     *
     * @return the boards
     */
    public Set<Board> getBoards() {
        return boards;
    }

    /**
     * Sets boards.
     *
     * @param boards the boards
     */
    public void setBoards(Set<Board> boards) {
        this.boards = boards;
    }

    /**
     * Add to boards.
     *
     * @param board the board
     */
    public void addToBoards(Board board) {
        this.boards.add(board);
    }

    /**
     * Remove from boards.
     *
     * @param board the board
     */
    public void removeFromBoards(Board board) {
        this.boards.remove(board);
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
