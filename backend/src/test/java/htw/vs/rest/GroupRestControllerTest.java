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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
    @Order(3)
    @WithMockUser(roles="SUPERVISOR")
    public void testAddGroup() throws Exception {
        this.mockMvc.perform(post("/groups/").param("coordinatorId","1").param("groupName","testgroup4")
                .param("boardName", "testboard5").param("location", "location5")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(anyOf(containsString("{\"id\":5,\"groupName\":\"testgroup4\",\"users\":[1],\"board\":{\"id\":6,\"boardName\":\"testboard5\",\"location\":\"location5\"},\"coordinator\":1}")
                        ,containsString("{\"id\":5,\"groupName\":\"testgroup4\",\"users\":[1],\"board\":{\"id\":7,\"boardName\":\"testboard5\",\"location\":\"location5\"},\"coordinator\":1}"))));
    }


    @Test
    public void testAddGroupWithoutRole() throws Exception {
        this.mockMvc.perform(post("/groups/").param("coordinatorId","1").param("groupName","testgroup4")
                .param("boardName", "testboard5").param("location", "location5")).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testAddGroupWithoutPermission() throws Exception {
        this.mockMvc.perform(post("/groups/").param("coordinatorId","1").param("groupName","testgroup4")
                .param("boardName", "testboard5").param("location", "location5")).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testAddGroupBoardNameNotUnique() throws Exception {
        this.mockMvc.perform(post("/groups/").param("coordinatorId","1").param("groupName","testgroup4")
                .param("boardName", "testboard2").param("location", "location5")).andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testAddGroupUserNotFound() throws Exception {
        this.mockMvc.perform(post("/groups/").param("coordinatorId","90").param("groupName","testgroup4")
                .param("boardName", "testboard5").param("location", "location5")).andDo(print()).andExpect(status().isNotFound());
    }

    @Test
    @Order(4)
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroup() throws Exception {

        String requestJson = "{\n" +
                "  \"id\" : 2,\n" +
                "  \"groupName\" : \"changedGroupName\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 2,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":2,\"groupName\":\"changedGroupName\",")));
        MvcResult result = this.mockMvc.perform(get("/users/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
    }

    @Test
    public void testReplaceGroupWithoutRole() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 2,\n" +
                "  \"groupName\" : \"changedGroupName\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 2,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles="USER")
    public void testReplaceGroupWithoutPermission() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 2,\n" +
                "  \"groupName\" : \"changedGroupName\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 2,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroupGroupNotFoundWithNewCoordinator() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 4,\n" +
                "  \"groupName\" : \"newGroupName\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 4,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 3 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/groups/4").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":4,\"groupName\":\"newGroupName\",")));
        MvcResult result = this.mockMvc.perform(get("/users/3")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroupGroupNotFoundWithCoordinator() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 4,\n" +
                "  \"groupName\" : \"newGroupName\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 4,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/groups/4").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":4,\"groupName\":\"newGroupName\",")));
        MvcResult result = this.mockMvc.perform(get("/users/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroupChangeCoordinator() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 1,\n" +
                "  \"groupName\" : \"newGroupName2\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 1,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 2 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/groups/1").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":1,\"groupName\":\"newGroupName2\",")));
        MvcResult result = this.mockMvc.perform(get("/users/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
        MvcResult result2 = this.mockMvc.perform(get("/users/4")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult2 = result2.getResponse().getContentAsString();
        assert(!stringResult2.contains("\"COORDINATOR\""));
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroupChangeBoard() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 3,\n" +
                "  \"groupName\" : \"boardChange\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 4,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 8 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/groups/3").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("\"board\":{\"id\":4,\"boardName\":\"testboard4\",\"location\":\"location4\"},\"coordinator\":8}")));
    }

    @Test
    @WithMockUser(roles="SUPERVISOR")
    public void testReplaceGroupNameNotUnique() throws Exception {

        String requestJson = "{\n" +
                "  \"id\" : 2,\n" +
                "  \"groupName\" : \"testgroup1\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 2,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n" +
                "  }\n" +
                "}";

        this.mockMvc.perform(post("/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isInternalServerError());
        MvcResult result = this.mockMvc.perform(get("/users/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert (stringResult.contains("\"COORDINATOR\""));
    }

    @Test
    @Order(3)
    @WithMockUser(roles="SUPERVISOR")
    public void testDeleteGroup() throws Exception {
        this.mockMvc.perform(delete("/groups/3").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isOk());
        MvcResult result = this.mockMvc.perform(get("/groups/")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(!stringResult.contains("\"id\":3,\"groupName\":\"testgroup3\""));
        MvcResult result2 = this.mockMvc.perform(get("/users/8")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult2 = result2.getResponse().getContentAsString();
        assert(!stringResult2.contains("\"COORDINATOR\""));
    }


    @Test
    @WithMockUser(roles="SUPERVISOR",username="Admin")
    public void testAddUserToGroup() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).param("userId","3")
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":1")));
        MvcResult result = this.mockMvc.perform(get("/users/1/groups")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert (stringResult.contains("\"id\":1,\"groupName\":"));
    }

    @Test
    public void testAddUserToGroupWithoutRole() throws Exception {
        this.mockMvc.perform(post("/groups/user/1").param("userId","3")
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
        ).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":1")));
        MvcResult result = this.mockMvc.perform(get("/users/1/groups")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert (stringResult.contains("\"id\":1,\"groupName\":"));
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
