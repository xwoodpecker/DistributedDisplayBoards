package htw.vs.rest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
public class BoardRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBoards() throws Exception {
        this.mockMvc.perform(get("/boards/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"boardName\":\"testboard1\"},{\"id\":2,\"boardName\":\"testboard2\"},{\"id\":3,\"boardName\":\"central\"}")));
    }

    @Test
    public void testGetBoard() throws Exception {
        this.mockMvc.perform(get("/boards/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"boardName\":\"testboard1\"}")));
    }

    @Test
    public void testAddBoard() throws Exception {
        //?
    }

    @Test
    public void testReplaceBoard() throws Exception {
        //?
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testDeleteBoard() throws Exception {
        this.mockMvc.perform(delete("/boards/1").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isOk());
    }
}
