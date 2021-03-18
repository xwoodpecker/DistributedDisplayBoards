package htw.vs.data;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


/**
 * The type Group repository test.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;


    /**
     * Test get coordinated groups.
     */
    @Test
    public void testGetCoordinatedGroups() {

        User coordinator1  = userRepository.findById(DbEntries.Coordinator1_Id).get();
        User coordinator2  = userRepository.findById(DbEntries.Coordinator2_Id).get();
        List<Group> groupList1 = groupRepository.getCoordinatedGroups(coordinator1);
        List<Group> groupList2 = groupRepository.getCoordinatedGroups(coordinator2);
        assert groupList1.stream().anyMatch(g -> g.getId() == DbEntries.BoardGroup1_ID);
        assert groupList2.stream().anyMatch(g -> g.getGroupName().equals(DbEntries.BoardGroup2_GroupName));
    }


}