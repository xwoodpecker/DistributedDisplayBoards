package htw.vs.rest;

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
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Message rest controller test.
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MessageRestControllerTest {
    @Autowired
    private MockMvc mockMvc;


    /**
     * Test get messages.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(1)
    @WithMockUser(roles="SUPERVISOR" )
    public void testGetMessages() throws Exception {
        this.mockMvc.perform(get("/api/messages/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"},")))
                .andExpect(content().string(containsString( "{\"id\":2,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":80,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"},")))
                .andExpect(content().string(containsString("{\"id\":3,")))
                .andExpect(content().string(containsString("\"user\":2,\"board\":1,\"displayTime\":120,\"endDate\":\"2021-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"},")))
                .andExpect(content().string(containsString("{\"id\":4,")))
                .andExpect(content().string(containsString("\"user\":3,\"board\":2,\"displayTime\":60,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"},")))
                .andExpect(content().string(containsString("{\"id\":5,")))
                .andExpect(content().string(containsString(",\"user\":5,\"board\":2,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":false,\"bgColor\":\"white\"}," )))
                .andExpect(content().string(containsString("{\"id\":6,")))
                .andExpect(content().string(containsString(",\"user\":6,\"board\":2,\"displayTime\":40,\"endDate\":\"2021-03-01 16:03:17\",\"active\":false,\"bgColor\":\"white\"},")))
                .andExpect(content().string(containsString("{\"id\":7,")))
                .andExpect(content().string(containsString(",\"user\":7,\"board\":3,\"displayTime\":40,\"endDate\":\"2021-03-01 16:03:17\",\"active\":false,\"bgColor\":\"white\"}")));
    }

    /**
     * Test get message.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR" )
    public void testGetMessage() throws Exception {
        this.mockMvc.perform(get("/api/messages/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")));
    }

    /**
     * Test get messages of group.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Coordinator1")
    public void testGetMessagesOfGroup() throws Exception {
        this.mockMvc.perform(get("/api/messages/group/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")))
                .andExpect(content().string(containsString( "{\"id\":2,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":80,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")))
                .andExpect(content().string(containsString("{\"id\":3,")))
                .andExpect(content().string(containsString("\"user\":2,\"board\":1,\"displayTime\":120,\"endDate\":\"2021-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")));
    }

    /**
     * Test get messages of board.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Coordinator1")
    public void testGetMessagesOfBoard() throws Exception {
        this.mockMvc.perform(get("/api/messages/board/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")))
                .andExpect(content().string(containsString( "{\"id\":2,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":80,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")))
                .andExpect(content().string(containsString("{\"id\":3,")))
                .andExpect(content().string(containsString("\"user\":2,\"board\":1,\"displayTime\":120,\"endDate\":\"2021-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")));
    }

    /**
     * Test get messages of user.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR" )
    public void testGetMessagesOfUser() throws Exception {
        this.mockMvc.perform(get("/api/messages/user/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")))
                .andExpect(content().string(containsString( "{\"id\":2,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":80,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")));
    }

    /**
     * Test get active messages of group.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Coordinator2")
    public void testGetActiveMessagesOfGroup() throws Exception {
        this.mockMvc.perform(get("/api/messages/active/group/2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":4,")))
                .andExpect(content().string(containsString("\"user\":3,\"board\":2,\"displayTime\":60,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")));
    }

    /**
     * Test get active messages of board.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Coordinator1" )
    public void testGetActiveMessagesOfBoard() throws Exception {
        this.mockMvc.perform(get("/api/messages/active/board/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")))
                .andExpect(content().string(containsString( "{\"id\":2,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":80,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")));
    }

    /**
     * Test get active messages of user.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR" )
    public void testGetActiveMessagesOfUser() throws Exception {
        this.mockMvc.perform(get("/api/messages/active/user/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("{\"id\":1,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")))
                .andExpect(content().string(containsString( "{\"id\":2,")))
                .andExpect(content().string(containsString("\"user\":1,\"board\":1,\"displayTime\":80,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}")));

        MvcResult result = this.mockMvc.perform(get("/api/messages/active/user/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(!stringResult.contains("{\"id\":5,\"content\":\"content of message 5\",\"user\":5,\"board\":2,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":false,\"bgColor\":\"white\"}"));
    }


    /**
     * Test add message.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR" )
    public void testAddMessage() throws Exception {
        String requestJson = "{\n" +
                "\"id\" : 90,\n" +
                "  \"content\" : \"Test Message Blubba Dubba\",\n" +
                "  \"user\" : {\n" +
                "   \"id\" : 2\n" +
                "   },\n" +
                "  \"board\" : {\n" +
                "   \"id\" : 1\n" +
                "   },\n" +
                "  \"displayTime\" : 120,\n" +
                "  \"endDate\" : \"2024-03-01 15:03:17\",\n" +
                "  \"active\" : true,\n" +
                "  \"bgColor\" : null \n" +
                "}";


        this.mockMvc.perform(post("/api/messages/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("\"content\":\"Test Message Blubba Dubba\",\"user\":2,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 15:03:17\",\"active\":true,")));
        MvcResult result = this.mockMvc.perform(get("/api/messages/user/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"id\":8,\"content\":\"Test Message Blubba Dubba\",\"user\":2,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 15:03:17\",\"active\":true,"));
    }

    /**
     * Test add message board not found.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR" )
    public void testAddMessageBoardNotFound() throws Exception {
        String requestJson = "{\n" +
                "\"id\" : 90,\n" +
                "  \"content\" : \"Test Message Blubba Dubba\",\n" +
                "  \"user\" : {\n" +
                "   \"id\" : 2\n" +
                "   },\n" +
                "  \"board\" : {\n" +
                "   \"id\" : 90\n" +
                "   },\n" +
                "  \"displayTime\" : 120,\n" +
                "  \"endDate\" : \"2024-03-01 15:03:17\",\n" +
                "  \"active\" : true,\n" +
                "  \"bgColor\" : null \n" +
                "}";
        this.mockMvc.perform(post("/api/messages/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isNotFound());
    }

    /**
     * Test add message user not found.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR" )
    public void testAddMessageUserNotFound() throws Exception {
        String requestJson = "{\n" +
                "\"id\" : 90,\n" +
                "  \"content\" : \"Test Message Blubba Dubba\",\n" +
                "  \"user\" : {\n" +
                "   \"id\" : 90\n" +
                "   },\n" +
                "  \"board\" : {\n" +
                "   \"id\" : 1\n" +
                "   },\n" +
                "  \"displayTime\" : 120,\n" +
                "  \"endDate\" : \"2024-03-01 15:03:17\",\n" +
                "  \"active\" : true,\n" +
                "  \"bgColor\" : null \n" +
                "}";

        this.mockMvc.perform(post("/api/messages/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isNotFound());
    }

    /**
     * Test add message without role.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAddMessageWithoutRole() throws Exception {
        String requestJson = "{\n" +
                "\"id\" : 90,\n" +
                "  \"content\" : \"Test Message Blubba Dubba\",\n" +
                "  \"user\" : {\n" +
                "   \"id\" : 2\n" +
                "   },\n" +
                "  \"board\" : {\n" +
                "   \"id\" : 1\n" +
                "   },\n" +
                "  \"displayTime\" : 120,\n" +
                "  \"endDate\" : \"2024-03-01 15:03:17\",\n" +
                "  \"active\" : true,\n" +
                "  \"bgColor\" : null \n" +
                "}";

        this.mockMvc.perform(post("/api/messages/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    /**
     * Test add message without permission.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="USER" )
    public void testAddMessageWithoutPermission() throws Exception {
        String requestJson = "{\n" +
                "\"id\" : 90,\n" +
                "  \"content\" : \"Test Message Blubba Dubba\",\n" +
                "  \"user\" : {\n" +
                "   \"id\" : 2\n" +
                "   },\n" +
                "  \"board\" : {\n" +
                "   \"id\" : 1\n" +
                "   },\n" +
                "  \"displayTime\" : 120,\n" +
                "  \"endDate\" : \"2024-03-01 15:03:17\",\n" +
                "  \"active\" : true,\n" +
                "  \"bgColor\" : null \n" +
                "}";

        this.mockMvc.perform(post("/api/messages/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isForbidden());
    }

    /**
     * Test delete message.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(2)
    @WithMockUser(roles="SUPERVISOR" )
    public void testDeleteMessage() throws Exception {
        this.mockMvc.perform(delete("/api/messages/7")).andDo(print()).andExpect(status().isOk());
        MvcResult result = this.mockMvc.perform(get("/api/messages/")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("{\"id\":1,"));
        assert(stringResult.contains("\"user\":1,\"board\":1,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}"));
        assert(stringResult.contains( "{\"id\":2,"));
        assert(stringResult.contains("\"user\":1,\"board\":1,\"displayTime\":80,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}"));
        assert(stringResult.contains("{\"id\":3,"));
        assert(stringResult.contains("\"user\":2,\"board\":1,\"displayTime\":120,\"endDate\":\"2021-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}"));
        assert(stringResult.contains("{\"id\":4,"));
        assert(stringResult.contains("\"user\":3,\"board\":2,\"displayTime\":60,\"endDate\":\"2024-03-01 16:03:17\",\"active\":true,\"bgColor\":\"white\"}"));
        assert(stringResult.contains("{\"id\":5,"));
        assert(stringResult.contains(",\"user\":5,\"board\":2,\"displayTime\":120,\"endDate\":\"2024-03-01 16:03:17\",\"active\":false,\"bgColor\":\"white\"}"));
        assert(stringResult.contains("{\"id\":6,"));
        assert(stringResult.contains(",\"user\":6,\"board\":2,\"displayTime\":40,\"endDate\":\"2021-03-01 16:03:17\",\"active\":false,\"bgColor\":\"white\"}"));
        assert(!stringResult.contains("{\"id\":7,"));
        assert(!stringResult.contains(",\"user\":7,\"board\":3,\"displayTime\":40,\"endDate\":\"2021-03-01 16:03:17\",\"active\":false,\"bgColor\":\"white\"}"));
    }
}
