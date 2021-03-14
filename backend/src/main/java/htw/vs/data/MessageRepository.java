package htw.vs.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * The interface Message repository.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * Find messages to display list.
     *
     * @return the list
     */
    @Query(value = "SELECT m from Message m WHERE m.active = true AND m.endDate < CURRENT_TIMESTAMP")
    List<Message> findMessagesToDisplay();
    List<Message> findMessagesByBoard(Board board);
    List<Message> findMessagesByUser(User user);
    @Query(value = "SELECT m from Message m WHERE m.active = true AND m.endDate > CURRENT_TIMESTAMP AND m.user = :user")
    List<Message> findActiveMessagesByUser(@Param("user")User user);
}
