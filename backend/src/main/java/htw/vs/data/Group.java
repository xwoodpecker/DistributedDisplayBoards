package htw.vs.data;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Group.
 */
@Entity
@Table(name = "BOARD_GROUPS")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "groupname",  unique = true, nullable = false)
    private String groupName;

    @JoinTable(name = "GROUPS_USERS")
    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Set<User> users = new HashSet<>();

    //this might change in the future
    // at the moment we have a onetoone relation but it is planned to have dynamic group assignments
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Board board;

    @OneToOne(fetch = FetchType.EAGER)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private User coordinator;


    /**
     * Instantiates a new Group.
     *
     * @param id          the id
     * @param groupName   the group name
     * @param users       the users
     * @param board       the board
     * @param coordinator the coordinator
     */
    public Group(Long id, String groupName, Set<User> users, Board board, User coordinator) {
        this.id = id;
        this.groupName = groupName;
        this.users = users;
        this.board = board;
        this.coordinator = coordinator;
    }

    /**
     * Instantiates a new Group.
     *
     * @param id          the id
     * @param groupName   the group name
     * @param board       the board
     * @param coordinator the coordinator
     */
    public Group(Long id, String groupName, Board board, User coordinator) {
        this.id = id;
        this.groupName = groupName;
        this.board = board;
        this.coordinator = coordinator;
    }

    /**
     * Instantiates a new Group.
     */
    public Group() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id);
    }
}
