package htw.vs.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Message repository.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT m from Message m WHERE m.active = true AND m.ttl < CURRENT_TIMESTAMP")
    List<Message> findMessagesToDisplay();
}
