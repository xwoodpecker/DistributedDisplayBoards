package htw.vs.data;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * The type Board.
 */
@Entity
@Table(name = "BOARDS")
public class Board {


    /**
     * The Id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "boardname",  unique = true, nullable = false)
    private String boardName;

    @Column(name = "location")
    private String location;


    @OneToOne(mappedBy = "board", fetch = FetchType.EAGER)
    @JsonIgnore
    private Group group;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "board", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Message> messages = new HashSet<>();

    /**
     * Instantiates a new Board.
     */
    public Board() {
    }

    /**
     * Instantiates a new Board.
     *
     * @param id the id
     */
    public Board(Long id) {
        this.id = id;
    }

    /**
     * Instantiates a new Board.
     *
     * @param id        the id
     * @param boardName the board name
     */
    public Board(Long id, String boardName) {
        this.id = id;
        this.boardName = boardName;
    }


    /**
     * Instantiates a new Board.
     *
     * @param id        the id
     * @param boardName the board name
     * @param group     the group
     * @param messages  the messages
     */
    public Board(Long id, String boardName, Group group, Set<Message> messages) {
        this.id = id;
        this.boardName = boardName;
        this.group = group;
        //this.messages = messages;
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
     * Gets board name.
     *
     * @return the board name
     */
    public String getBoardName() {
        return boardName;
    }

    /**
     * Sets board name.
     *
     * @param boardName the board name
     */
    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }


    /**
     * Gets group.
     *
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Sets group.
     *
     * @param group the group
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", boardName='" + boardName + '\'' +
                '}';
    }

    /**
     * Gets location.
     *
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets location.
     *
     * @param location the location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets messages.
     *
     * @return the messages
     */
    public Set<Message> getMessages() {
        return messages;
    }

    /**
     * Sets messages.
     *
     * @param messages the messages
     */
    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board b = (Board) o;
        return Objects.equals(id, b.id);
    }
}
