package htw.vs.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import htw.vs.data.Board;
import htw.vs.data.Group;
import htw.vs.data.Role;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.MultiValueMap;

import static org.hamcrest.Matchers.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    @WithMockUser(roles="SUPERVISOR")
    public void testAddGroup() throws Exception {
        this.mockMvc.perform(post("/groups/").param("boardId","4").param("coordinatorId","1").param("groupName","testgroup4"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":4,\"groupName\":\"testgroup4\",\"users\":[],\"board\":4,\"coordinator\":1}")));
    }

    @Test
    public void testAddGroupWithoutRole() throws Exception {
        this.mockMvc.perform(post("/groups/").param("boardId","4").param("coordinatorId","1").param("groupName","testgroup4"))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testAddGroupWithoutPermission() throws Exception {
        this.mockMvc.perform(post("/groups/").param("boardId","4").param("coordinatorId","1").param("groupName","testgroup4"))
                .andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testAddGroupBoardNotFound() throws Exception {
        this.mockMvc.perform(post("/groups/").param("boardId","90").param("coordinatorId","1").param("groupName","testgroup4"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testAddGroupUserNotFound() throws Exception {
        this.mockMvc.perform(post("/groups/").param("boardId","4").param("coordinatorId","90").param("groupName","testgroup4"))
                .andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroup() throws Exception {
        Board board = new Board();
        board.setId(2l);

        User coordinator = new User();
        coordinator.setId(5l);

        Group group = new Group();
        group.setId(2l);
        group.setGroupName("changedBoardName");
        group.setBoard(board);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(group);

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andExpect(status().isOk());
    }

    @Test
    public void testReplaceGroupWithoutRole() throws Exception {
        Board board = new Board();
        board.setId(2l);

        User coordinator = new User();
        coordinator.setId(5l);

        Group group = new Group();
        group.setId(2l);
        group.setGroupName("changedBoardName");
        group.setBoard(board);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(group);

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testReplaceGroupWithoutPermission() throws Exception {
        Board board = new Board();
        board.setId(2l);

        User coordinator = new User();
        coordinator.setId(5l);

        Group group = new Group();
        group.setId(2l);
        group.setGroupName("changedBoardName");
        group.setBoard(board);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(group);

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroupGroupNotFound() throws Exception {
        Board board = new Board();
        board.setId(2l);

        User coordinator = new User();
        coordinator.setId(5l);

        Group group = new Group();
        group.setId(4l);
        group.setGroupName("changedBoardName");
        group.setBoard(board);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(group);

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andExpect(status().isOk());
    }

    @Test
    @Order(3)
    @WithMockUser(roles="SUPERVISOR")
    public void testDeleteGroup() throws Exception {
        this.mockMvc.perform(delete("/groups/3").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isOk());
        MvcResult result = this.mockMvc.perform(get("/groups/")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(!stringResult.contains("\"id\":3,\"groupName\":\"testgroup3\""));
    }

    //todo

    @Test
    @WithMockUser(roles="SUPERVISOR",username="Admin")
    public void testAddUserToGroup() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","3")
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":1,\"groupName\":\"testgroup1\"")));
    }

    @Test
    public void testAddUserToGroupWithoutRole() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","3")
        ).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER",username="User1")
    public void testAddUserToGroupWithoutPermission() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","3")
        ).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="COORDINATOR",username="Coordinator1")
    public void testAddUserToGroupAsCoordinator() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","3")
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":1,\"groupName\":\"testgroup1\"")));
    }

    @Test
    @WithMockUser(roles="COORDINATOR",username="Coordinator2")
    public void testAddUserToGroupDiffernetCoordinator() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","3")
        ).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR",username="Admin")
    public void testAddUserToGroupUserNotFound() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","90")
        ).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR",username="Admin")
    public void testAddUserToGroupGroupNotFound() throws Exception {
        this.mockMvc.perform(post("/groups/user/90").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","3")
        ).andDo(print()).andExpect(status().isNotFound());
    }
}
