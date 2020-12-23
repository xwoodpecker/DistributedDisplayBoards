package htw.vs.data;


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

    @Column(name = "boardname")
    private String boardName;

    /**
     * Instantiates a new Board.
     */
    public Board() {
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

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", boardName='" + boardName + '\'' +
                '}';
    }
}
