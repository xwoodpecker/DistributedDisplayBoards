package htw.vs.data;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type Web socket message.
 */
@Entity
@Table(name = "MESSAGES")
public class Message  { //implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender")
    @JsonIgnoreProperties({"userName", "password", "enabled", "email", "roles", "groups" })
    private User user;


    @ManyToOne
    @JoinColumn(name = "board")
    @JsonIgnoreProperties({"boardName"})
    private Board board;


    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "active")
    private boolean active;



    public Message() {

    }

    public Message(Message other) {
        this.id = other.id;
        this.content = other.content;
        this.user = other.user;
        this.board = other.board;
        this.endDate = other.endDate;
        this.active = other.active;
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
     * Gets content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets content.
     *
     * @param content the content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(User user) {
        this.user = user;
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
     * Gets endDate.
     *
     * @return the endDate
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * Sets endDate.
     *
     * @param endDate the endDate
     */
    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}