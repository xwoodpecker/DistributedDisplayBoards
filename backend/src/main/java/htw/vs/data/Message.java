package htw.vs.data;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The type Message.
 */
@Entity
@Table(name = "MESSAGES")
public class Message  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="id")
    @JsonIdentityReference(alwaysAsId=true)
    private Board board;

    @Column(name = "display_time")
    private Integer displayTime;

    @Column(name = "end_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp endDate;

    @Column(name = "active")
    private boolean active;

    @Column(name = "bg_color")
    private String bgColor;

    /**
     * Instantiates a new Message.
     */
    public Message() {

    }

    /**
     * Instantiates a new Message.
     *
     * @param other the other
     */
    public Message(Message other) {
        this.id = other.id;
        this.content = other.content;
        this.user = other.user;
        this.board = other.board;
        this.endDate = other.endDate;
        this.active = other.active;
        this.bgColor = other.bgColor;
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
     * Gets end date.
     *
     * @return the end date
     */
    public Timestamp getEndDate() {
        return endDate;
    }

    /**
     * Sets end date.
     *
     * @param endDate the end date
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

    /**
     * Gets display time.
     *
     * @return the display time
     */
    public Integer getDisplayTime() {
        return displayTime;
    }

    /**
     * Sets display time.
     *
     * @param displayTime the display time
     */
    public void setDisplayTime(Integer displayTime) {
        this.displayTime = displayTime;
    }

    /**
     * Gets bg color.
     *
     * @return the bg color
     */
    public String getBgColor() {
        return bgColor;
    }

    /**
     * Sets bg color.
     *
     * @param bgColor the bg color
     */
    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }
}