package htw.vs.data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * The type Groups.
 */
@Entity
@Table(name = "BOARD_GROUPS")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupname")
    private String groupName;

    @JoinTable(name = "GROUP_USERS")
    @OneToMany(fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    private Board board;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    private User coordinator;

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
     * Gets users.
     *
     * @return the users
     */
    public Set<User> getUsers() {
        return users;
    }

    /**
     * Sets users.
     *
     * @param users the users
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Add user.
     *
     * @param user the user
     */
    public void addUser(User user) {
        this.users.add(user);
    }

    /**
     * Gets board.
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets board.
     *
     * @param board the board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Gets coordinator.
     *
     * @return the coordinator
     */
    public User getCoordinator() {
        return coordinator;
    }

    /**
     * Sets coordinator.
     *
     * @param coordinator the coordinator
     */
    public void setCoordinator(User coordinator) {
        this.coordinator = coordinator;
    }

    /**
     * Gets group name.
     *
     * @return the group name
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets group name.
     *
     * @param groupName the group name
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "Groups{" +
                "id=" + id +
                ", users=" + users +
                ", boards=" + board +
                ", coordinator=" + coordinator +
                '}';
    }
}
