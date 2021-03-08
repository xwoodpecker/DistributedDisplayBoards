package htw.vs.data;


import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;

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


    @OneToOne(mappedBy = "board", fetch = FetchType.EAGER)
    @JsonIgnore
    private Group group;

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

}
