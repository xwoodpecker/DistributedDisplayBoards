package htw.vs.rest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import htw.vs.data.Role;
import htw.vs.data.RoleRepository;
import htw.vs.data.User;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRestControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

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
    @Order(3)
    @WithMockUser(username = "User1", password = "123456", roles="USER")
    public void testGetUserByLoginCredentials() throws Exception {
        this.mockMvc.perform(get("/users/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString(
                        "\"id\":1,\"userName\":\"User1\",\"password\":\"123456\",\"enabled\":true,\"email\":\"User1@mail\"")));
    }

    @Test
    @Order(4)
    @WithMockUser(roles="SUPERVISOR")
    public void testAddUser() throws Exception {
        this.mockMvc.perform(post("/users/").param("userName","User10").param("password","123456").param("email","User10@mail").param("isSupervisor", "false"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":10,\"userName\":\"User10\"")));
    }

    @Test
    public void testAddUserWithoutRole() throws Exception {
        this.mockMvc.perform(post("/users/").param("userName","User10").param("password","123456").param("email","User10@mail").param("isSupervisor", "false"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testAddUserWithoutPermission() throws Exception {
        this.mockMvc.perform(post("/users/").param("userName","User10").param("password","123456").param("email","User10@mail").param("isSupervisor", "false"))
                .andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testAddUserUserSameName() throws Exception {
        this.mockMvc.perform(post("/users/").param("userName","User1").param("password","123456").param("email","User11@mail").param("isSupervisor", "false"))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testAddUserUserSameEmail() throws Exception {
        this.mockMvc.perform(post("/users/").param("userName","User11").param("password","123456").param("email","User1@mail").param("isSupervisor", "false"))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    @Order(7)
    @WithMockUser(roles="SUPERVISOR")
    public void testAddUserUserAsSupervisor() throws Exception {
        this.mockMvc.perform(post("/users/").param("userName","User11").param("password","123456").param("email","User11@mail").param("isSupervisor", "true"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("\"id\":11,\"userName\":\"User11\"")));
        MvcResult result = this.mockMvc.perform(get("/users/11")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"SUPERVISOR\""));

    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testAddUserLongName() throws Exception {
        this.mockMvc.perform(post("/users/").param("userName","User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11User11")
                .param("password","123456").param("email","User1@mail").param("isSupervisor", "false"))
                .andDo(print()).andExpect(status().isInternalServerError());
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
    @Order(8)
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceUser() throws Exception {
        Role userRole = roleRepository.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);

        User user = new User(3l);
        user.setUserName("User90");
        user.setPassword("123456");
        user.setEmail("User90@mail");
        user.setEnabled(false);
        user.setRoles(roleSet);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);

        this.mockMvc.perform(post("/users/3").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        MvcResult result = this.mockMvc.perform(get("/users/3")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"id\":3,\"userName\":\"User90\""));
        assert(stringResult.contains("\"enabled\":false,\"email\":\"User90@mail\",\"roles\":[{\"id\":3,\"name\":\"USER\"}]"));
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceUserOnlyName() throws Exception {

        User user = new User(3l);
        user.setUserName("User77");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);

        this.mockMvc.perform(post("/users/3").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        MvcResult result = this.mockMvc.perform(get("/users/3")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"id\":3,\"userName\":\"User77\""));
        assert(stringResult.contains("\"enabled\":false,\"email\":\"User90@mail\",\"roles\":[{\"id\":3,\"name\":\"USER\"}]"));
    }

    @Test
    public void testReplaceUserWithoutRole() throws Exception {

        User user = new User(3l);
        user.setUserName("User77");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user);

        this.mockMvc.perform(post("/users/3").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testReplaceUserWithoutPermission() throws Exception {

        User user = new User(3l);
        user.setUserName("User77");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(user);

        this.mockMvc.perform(post("/users/3").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @Order(9)
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceUserNewUser() throws Exception {

        Role userRole = roleRepository.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(userRole);

        User user = new User(80l);
        user.setUserName("User80");
        user.setPassword("123456");
        user.setEmail("User80@mail");
        user.setEnabled(true);
        user.setRoles(roleSet);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(user);

        this.mockMvc.perform(post("/users/80").contentType(MediaType.APPLICATION_JSON).content(requestJson)).andDo(print())
                .andExpect(status().isOk()).andExpect(content().string(containsString("")));
        MvcResult result = this.mockMvc.perform(get("/users/12")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"id\":12,\"userName\":\"User80\""));
        assert(stringResult.contains("\"enabled\":true,\"email\":\"User80@mail\",\"roles\":[{\"id\":3,\"name\":\"USER\"}]"));
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

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testGetGroupsOfUserAsSupervisor() throws Exception {
        this.mockMvc.perform(get("/users/6/groups")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("")));

    }

}
