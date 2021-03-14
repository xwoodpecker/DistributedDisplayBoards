package htw.vs.rest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import htw.vs.data.User;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRestControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    public void testGetUsers() throws Exception {
        this.mockMvc.perform(get("/users/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(
                          "\"id\":1,\"userName\":\"User1\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User1@mail\"",
                                    "\"id\":2,\"userName\":\"User2\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User2@mail\"",
                                    "\"id\":3,\"userName\":\"User3\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User3@mail\"",
                                    "\"id\":4,\"userName\":\"Coordinator1\",\"password\":\"123456\",\"enabled\":true,\"email\":\"Coordinator1@mail\"",
                                    "\"id\":5,\"userName\":\"Coordinator2\",\"password\":\"123456\",\"enabled\":true,\"email\":\"Coordinator2@mail\"",
                                    "\"id\":6,\"userName\":\"Admin\",\"password\":\"123456\",\"enabled\":true,\"email\":\"Admin@mail\"",
                                    "\"id\":7,\"userName\":\"User4\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User4@mail\"")));
    }

    @Test
    @Order(2)
    public void testGetUser() throws Exception {
        this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1,\"userName\":\"User1\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User1@mail\"")));
    }

    @Test
    @WithMockUser(username = "User1", password = "123456", roles="USER")
    public void testGetUserByLoginCredentials() throws Exception {
        this.mockMvc.perform(get("/users/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1,\"userName\":\"User1\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User1@mail\"")));
    }

    @Test
    public void testAddUser() throws Exception {
        //todo julian pls <3
    }

    @Test
    @Order(5)
    @WithMockUser(username = "User1", password = "123456", roles="USER")
    public void testChangeOwnPassword() throws Exception {
        this.mockMvc.perform(post("/users/password/own").param("newPassword","qwerasdf")).andDo(print()).andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(stringResult);
        JsonNode passwordNode = rootNode.get("password");
        String textValue = passwordNode.textValue();
        assert(passwordEncoder.matches("qwerasdf", textValue));
    }


    @Test
    @Order(6)
    @WithMockUser(roles="SUPERVISOR")
    public void testChangeOtherPassword() throws Exception {
        this.mockMvc.perform(post("/users/password/other").param("username", "User1").param("newPassword","asdfqwer")).andDo(print()).andExpect(status().isOk());

        MvcResult result = this.mockMvc.perform(get("/users/1")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(stringResult);
        JsonNode passwordNode = rootNode.get("password");
        String textValue = passwordNode.textValue();
        assert(passwordEncoder.matches("asdfqwer", textValue));
    }


    @Test
    public void testReplaceUser() throws Exception {
        //todo julian pls <3
    }

    @Test
    @Order(3)
    @WithMockUser(roles="SUPERVISOR")
    public void testDeleteUser() throws Exception {
        this.mockMvc.perform(delete("/users/7").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isOk());
        MvcResult result = this.mockMvc.perform(get("/users/")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(!stringResult.contains("\"id\":7,\"userName\":\"User4\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User4@mail\""));
    }


    @Test
    @Order(4)
    @WithMockUser(roles="SUPERVISOR")
    public void testDeleteUserCoordinator() throws Exception {
        this.mockMvc.perform(delete("/users/8").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetGroupsOfUser() throws Exception {
        this.mockMvc.perform(get("/users/1/groups")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1,\"groupName\":\"testgroup1\"")));

    }

}
