package htw.vs.rest;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import htw.vs.data.Board;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * The type Board rest controller test.
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BoardRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    /**
     * Test get boards.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(1)
    public void testGetBoards() throws Exception {
        this.mockMvc.perform(get("/api/boards/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"boardName\":\"testboard1\",\"location\":\"location1\"}," +
                        "{\"id\":2,\"boardName\":\"testboard2\",\"location\":\"location2\"}," +
                        "{\"id\":3,\"boardName\":\"testboard3\",\"location\":\"location3\"}," +
                        "{\"id\":4,\"boardName\":\"testboard4\",\"location\":\"location4\"}," +
                        "{\"id\":5,\"boardName\":\"testboard5\",\"location\":\"location5\"}," +
                        "{\"id\":6,\"boardName\":\"central\",\"location\":null}")));


    }

    /**
     * Test get board.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetBoard() throws Exception {
        this.mockMvc.perform(get("/api/boards/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"boardName\":\"testboard1\",\"location\":\"location1\"}")));
    }

    /**
     * Test add board.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(2)
    @WithMockUser(roles="SUPERVISOR" )
    public void testAddBoard() throws Exception {
        Board board = new Board();
        board.setBoardName("addedBoardTest");
        board.setLocation("newlocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("{\"id\":7,\"boardName\":\"addedBoardTest\",\"location\":\"newlocation\"}")));
    }

    /**
     * Test add board without role.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAddBoardWithoutRole() throws Exception {
        Board board = new Board();
        board.setBoardName("addedBoardTest");
        board.setLocation("newlocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isUnauthorized());
    }

    /**
     * Test add board without permission.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="USER")
    public void testAddBoardWithoutPermission() throws Exception {
        Board board = new Board();
        board.setBoardName("addedBoardTest");
        board.setLocation("newlocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isForbidden());
    }

    /**
     * Test replace board.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testReplaceBoard() throws Exception {
        Board board = new Board(1l);
        board.setBoardName("replacedBoardName1");
        board.setLocation("newlocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/1").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,\"boardName\":\"replacedBoardName1\",\"location\":\"newlocation\"}")));
    }

    /**
     * Test replace board id not found.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testReplaceBoardIdNotFound() throws Exception {
        Board board = new Board(90l);
        board.setBoardName("newBoardName");
        board.setLocation("newLocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/90").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":8,\"boardName\":\"newBoardName\",\"location\":\"newLocation\"}")));
    }

    /**
     * Test replace board without role.
     *
     * @throws Exception the exception
     */
    @Test
    public void testReplaceBoardWithoutRole() throws Exception {
        Board board = new Board(1l);
        board.setBoardName("replacedBoardName3");
        board.setLocation("newlocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/1").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isUnauthorized());
    }

    /**
     * Test replace board without permission.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="USER", username = "User1")
    public void testReplaceBoardWithoutPermission() throws Exception {
        Board board = new Board(1l);
        board.setBoardName("replacedBoardName3");
        board.setLocation("newlocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/1").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isForbidden());
    }

    /**
     * Test replace board existing name.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testReplaceBoardExistingName() throws Exception {
        Board board = new Board(1l);
        board.setBoardName("testboard2");
        board.setLocation("newlocation");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(board);

        this.mockMvc.perform(post("/api/boards/1").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isInternalServerError());
    }
}
