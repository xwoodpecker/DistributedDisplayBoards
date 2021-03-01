package htw.vs.data;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;


    @Test
    public void testFindMessagesToDisplay() {
        List<Message> messages = messageRepository.findMessagesToDisplay();
        assert messages.size() == DbEntries.MessagesToDisplay;
    }

    //todo julian add your other tests pls

}