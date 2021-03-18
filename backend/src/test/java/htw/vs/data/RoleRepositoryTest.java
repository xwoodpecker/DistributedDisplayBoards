package htw.vs.data;


import htw.vs.base.Const;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserRepository userRepository;


    @Test
    public void testFindByName() {
        User supervisor = userRepository.findById(DbEntries.Supervisor_Id).get();
        Role supervisorRole = roleRepository.findByName(Const.SUPERVISOR_ROLE);
        assert supervisorRole.getUsers().stream().anyMatch(u -> u.getId() == supervisor.getId());
        assert supervisorRole.getUsers().size() == 1;


        User coordinator1 = userRepository.findById(DbEntries.Coordinator1_Id).get();
        User coordinator2 = userRepository.findById(DbEntries.Coordinator2_Id).get();
        Role coordinatorRole = roleRepository.findByName(Const.COORDINATOR_ROLE);
        assert coordinatorRole.getUsers().stream().anyMatch(u -> u.getId() == coordinator1.getId());
        assert coordinatorRole.getUsers().stream().anyMatch(u -> u.getId() == coordinator2.getId());
        assert coordinatorRole.getUsers().size() == 3;


        Role userRole = roleRepository.findByName(Const.USER_ROLE);
        assert userRole.getUsers().size() == 8;


    }


}