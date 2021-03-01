package htw.vs.data;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void testFindUserByUserName() {
        User user1  = userRepository.findById(DbEntries.User1_Id).get();
        User u = userRepository.findUserByUserName(DbEntries.User1_Username);
        assertEquals(user1.getId(), u.getId());
        User user2  = userRepository.findById(DbEntries.User2_Id).get();
        u = userRepository.findUserByUserName(DbEntries.User2_Username);
        assertEquals(user2.getId(), u.getId());
        User user3  = userRepository.findById(DbEntries.User3_Id).get();
        u = userRepository.findUserByUserName(DbEntries.User3_Username);
        assertEquals(user3.getId(), u.getId());

        User coordinator1  = userRepository.findById(DbEntries.Coordinator1_Id).get();
        u = userRepository.findUserByUserName(DbEntries.Coordinator1_Username);
        assertEquals(coordinator1.getId(), u.getId());
        Role coordinatorRole = roleRepository.findByName(DbEntries.COORDINATOR_ROLE);
        assert u.getRoles().contains(coordinatorRole);
        User coordinator2  = userRepository.findById(DbEntries.Coordinator2_Id).get();
        u = userRepository.findUserByUserName(DbEntries.Coordinator2_Username);
        assertEquals(coordinator2.getId(), u.getId());
        assert u.getRoles().contains(coordinatorRole);

        User supervisor  = userRepository.findById(DbEntries.Supervisor_Id).get();
        u = userRepository.findUserByUserName(DbEntries.Supervisor_Username);
        assertEquals(supervisor.getId(), u.getId());
        Role supervisorRole = roleRepository.findByName(DbEntries.SUPERVISOR_ROLE);
        assert u.getRoles().contains(supervisorRole);
    }


}