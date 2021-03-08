package htw.vs.rest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testGetGroups() throws Exception {
        this.mockMvc.perform(get("/groups/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(
                        "\"id\":1,\"groupName\":\"testgroup1\"", "\"id\":2,\"groupName\":\"testgroup2\"", "\"id\":3,\"groupName\":\"testgroup3\"")));
    }

    @Test
    @Order(2)
    public void testGetGroup() throws Exception {
        this.mockMvc.perform(get("/groups/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1,\"groupName\":\"testgroup1\"")));
    }

    @Test
    @Order(3)
    public void testAddGroup() throws Exception {
        //?
    }

    @Test
    @Order(4)
    public void testReplaceGroup() throws Exception {
        //?
    }

    @Test
    @Order(5)
    @WithMockUser(roles="SUPERVISOR")
    public void testDeleteGroup() throws Exception {
        this.mockMvc.perform(delete("/groups/3").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isOk());
    }

    //todo
}
