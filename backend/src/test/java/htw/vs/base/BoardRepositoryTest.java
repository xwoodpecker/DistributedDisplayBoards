package htw.vs.base;


import htw.vs.data.Board;
import htw.vs.data.BoardRepository;
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
    public void testGetCoordinatedGroups() {
        Board board1  = boardRepository.findById(DbEntries.Board1_Id).get();
        Board b = boardRepository.findBoardByBoardName(DbEntries.Board1_BoardName);
        assertEquals(board1.getId(), b.getId());
    }


}