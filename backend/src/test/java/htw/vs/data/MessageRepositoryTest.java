package htw.vs.data;


import htw.vs.base.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


/**
 * The type Message repository test.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * Test find messages to display.
     */
    @Test
    public void testFindMessagesToDisplay() {
        List<Message> messages = messageRepository.findMessagesToDisplay();
        assert messages.size() == 3;
    }

    /**
     * Test find messages by board.
     */
    @Test
    public void testFindMessagesByBoard(){
        List<Message> messages = messageRepository.findMessagesByBoard(boardRepository.findById(1l).get());
        assert messages.size() == 3;
    }

    /**
     * Test find messages by user.
     */
    @Test
    public void testFindMessagesByUser(){
        List<Message> messages = messageRepository.findMessagesByUser(userRepository.findById(1l).get());
        assert messages.size() == 2;
    }

    /**
     * Test find active messages by user.
     */
    @Test
    public void testFindActiveMessagesByUser(){
        List<Message> messages = messageRepository.findActiveMessagesByUser(userRepository.findById(1l).get());
        assert messages.size() == 2;
    }

    /**
     * Test find active messages by board.
     */
    @Test
    public void testFindActiveMessagesByBoard(){
        List<Message> messages = messageRepository.findActiveMessagesByBoard(boardRepository.findById(2l).get());
        assert messages.size() == 1;
    }
}