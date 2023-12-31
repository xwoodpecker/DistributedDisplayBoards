package htw.vs.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by user name user.
     *
     * @param userName the user name
     * @return the user
     */
    User findUserByUserName(String userName);

    List<User> findByGroupsContaining(Group group);
}
