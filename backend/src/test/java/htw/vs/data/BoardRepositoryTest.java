package htw.vs.data;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void testFindBoardByBoardName() {
        Board board1  = boardRepository.findById(DbEntries.Board1_Id).get();
        Board b = boardRepository.findBoardByBoardName(DbEntries.Board1_BoardName);
        assertEquals(board1.getId(), b.getId());
        Board board2  = boardRepository.findById(DbEntries.Board2_Id).get();
        b = boardRepository.findBoardByBoardName(DbEntries.Board2_BoardName);
        assertEquals(board2.getId(), b.getId());
    }


}