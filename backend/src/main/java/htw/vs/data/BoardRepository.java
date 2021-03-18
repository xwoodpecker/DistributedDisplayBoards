package htw.vs.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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


    /**
     * Find board by id eager group board.
     *
     * @param id the id
     * @return the board
     */
    @Query("select b from Board b join fetch b.group where b.id = ?1")
    Board findBoardByIdEagerGroup(Long id);
}
