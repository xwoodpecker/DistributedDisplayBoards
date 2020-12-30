package htw.vs.data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type Web socket message.
 */
@Entity
@Table(name = "MESSAGES")
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "sender")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board")
    private Board board;

    @Column(name = "ttl")
    private Timestamp ttl;

    @Column(name = "active")
    private boolean active;

    public Message() {

    }

    public Message(Message other) {
        this.id = other.id;
        this.content = other.content;
        this.user = other.user;
        this.board = other.board;
        this.ttl = other.ttl;
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
     * Gets ttl.
     *
     * @return the ttl
     */
    public Timestamp getTtl() {
        return ttl;
    }

    /**
     * Sets ttl.
     *
     * @param ttl the ttl
     */
    public void setTtl(Timestamp ttl) {
        this.ttl = ttl;
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