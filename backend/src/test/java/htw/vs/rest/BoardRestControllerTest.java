package htw.vs.rest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void testGetBoards() throws Exception {
        this.mockMvc.perform(get("/boards/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"boardName\":\"testboard1\"},{\"id\":2,\"boardName\":\"testboard2\"},{\"id\":3,\"boardName\":\"testboard3\"}")));
    }

    @Test
    @Order(2)
    public void testGetBoard() throws Exception {
        this.mockMvc.perform(get("/boards/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"boardName\":\"testboard1\"}")));
    }

    @Test
    @Order(3)
    @WithMockUser(roles="SUPERVISOR" )
    public void testAddBoard() throws Exception {
        this.mockMvc.perform(post("/boards/").param("boardName", "addedBoardTest")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testAddBoardWithoutPermission() throws Exception {
        this.mockMvc.perform(post("/boards/").param("boardName", "addedBoardTest")).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @Order(4)
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceBoard() throws Exception {
        this.mockMvc.perform(post("/boards/1").param("boardName", "replacedBoardName1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceBoardIdNotFound() throws Exception {
        this.mockMvc.perform(post("/boards/5").param("boardName", "replacedBoardName2")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testReplaceBoardWithoutPermission() throws Exception {
        this.mockMvc.perform(post("/boards/1").param("boardName", "replacedBoardName3")).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceBoardExistingName() throws Exception {
        this.mockMvc.perform(post("/boards/1").param("boardName", "testboard2")).andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    @Order(5)
    @WithMockUser(roles="SUPERVISOR")
    public void testDeleteBoard() throws Exception {
        this.mockMvc.perform(delete("/boards/3").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isOk());
    }
}
