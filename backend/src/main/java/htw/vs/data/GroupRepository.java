package htw.vs.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Query(value = "SELECT g from Group g WHERE g.coordinator = :user")
    List<Group> getCoordinatedGroups(@Param("user")User user);
}
