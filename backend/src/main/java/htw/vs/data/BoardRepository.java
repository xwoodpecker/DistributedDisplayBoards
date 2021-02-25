package htw.vs.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Board repository.
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    /**
     * Find board by board name board.
     *
     * @param boardName the board name
     * @return the board
     */
    Board findBoardByBoardName(String boardName);
}
