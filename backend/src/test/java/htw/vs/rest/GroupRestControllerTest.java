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

/**
 * The type Group rest controller test.
 */
@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupRestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    /**
     * Test get groups.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(1)
    public void testGetGroups() throws Exception {
        this.mockMvc.perform(get("/api/groups/")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(stringContainsInOrder(
                        "\"id\":1,\"groupName\":\"testgroup1\"", "\"id\":2,\"groupName\":\"testgroup2\"", "\"id\":3,\"groupName\":\"testgroup3\"")));
    }

    /**
     * Test get group.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(2)
    public void testGetGroup() throws Exception {
        this.mockMvc.perform(get("/api/groups/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("\"id\":1,\"groupName\":\"testgroup1\"")));
    }

    /**
     * Test add group.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(3)
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testAddGroup() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 5,\n" +
                "  \"groupName\" : \"testgroup4\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 6,\n" +
                "    \"boardName\" : \"testboard6\",\n" +
                "    \"location\" : \"location6\"\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 1 \n" +
                "  }\n" +
                "}";

        this.mockMvc.perform(post("/api/groups/").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(anyOf(containsString("{\"id\":5,\"groupName\":\"testgroup4\",\"users\":[1],\"board\":{\"id\":7,\"boardName\":\"testboard6\",\"location\":\"location6\"},\"coordinator\":1}")
                        ,containsString("{\"id\":5,\"groupName\":\"testgroup4\",\"users\":[1],\"board\":{\"id\":9,\"boardName\":\"testboard6\",\"location\":\"location6\"},\"coordinator\":1}"))));
    }


    /**
     * Test add group without role.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAddGroupWithoutRole() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 5,\n" +
                "  \"groupName\" : \"testgroup4\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 6,\n" +
                "    \"boardName\" : \"testboard5\",\n" +
                "    \"location\" : \"location5\"\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 1 \n" +
                "  }\n" +
                "}";

        this.mockMvc.perform(post("/api/groups/").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    /**
     * Test add group without permission.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="USER")
    public void testAddGroupWithoutPermission() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 5,\n" +
                "  \"groupName\" : \"testgroup4\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 6,\n" +
                "    \"boardName\" : \"testboard5\",\n" +
                "    \"location\" : \"location5\"\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 1 \n" +
                "  }\n" +
                "}";

        this.mockMvc.perform(post("/api/groups/").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isForbidden());
    }

    /**
     * Test add group board name not unique.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testAddGroupBoardNameNotUnique() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 5,\n" +
                "  \"groupName\" : \"testgroup4\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 6,\n" +
                "    \"boardName\" : \"testboard2\",\n" +
                "    \"location\" : \"location5\"\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 1 \n" +
                "  }\n" +
                "}";

        this.mockMvc.perform(post("/api/groups/").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    /**
     * Test add group user not found.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testAddGroupUserNotFound() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 5,\n" +
                "  \"groupName\" : \"testgroup4\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 6,\n" +
                "    \"boardName\" : \"testboard5\",\n" +
                "    \"location\" : \"location5\"\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 90 \n" +
                "  }\n" +
                "}";

        this.mockMvc.perform(post("/api/groups/").contentType(APPLICATION_JSON).content(requestJson))
                .andDo(print()).andExpect(status().isNotFound());
    }

    /**
     * Test replace group.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(4)
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
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

        this.mockMvc.perform(post("/api/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":2,\"groupName\":\"changedGroupName\",")));
        MvcResult result = this.mockMvc.perform(get("/api/users/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
        MvcResult result2 = this.mockMvc.perform(get("/api/boards/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult2 = result2.getResponse().getContentAsString();
        assert(stringResult2.contains("\"id\":2,\"boardName\":\"testboard2\",\"location\":\"location2\""));
    }

    /**
     * Test replace group.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(5)
    @WithMockUser(roles="SUPERVISOR", username="Admin")
    public void testReplaceGroupNewGroup() throws Exception {

        String requestJson = "{\n" +
                "  \"id\" : 90,\n" +
                "  \"groupName\" : \"newGroup\",\n" +
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

        this.mockMvc.perform(post("/api/groups/90").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(anyOf(containsString("{\"id\":5,\"groupName\":\"newGroup\","),containsString("{\"id\":6,\"groupName\":\"newGroup\","))));

        MvcResult result = this.mockMvc.perform(get("/api/users/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
    }

    /**
     * Test replace group without role.
     *
     * @throws Exception the exception
     */
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

        this.mockMvc.perform(post("/api/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isUnauthorized());
    }

    /**
     * Test replace group without permission.
     *
     * @throws Exception the exception
     */
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

        this.mockMvc.perform(post("/api/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isForbidden());
    }

    /**
     * Test replace group group not found with new coordinator.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
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

        this.mockMvc.perform(post("/api/groups/4").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":4,\"groupName\":\"newGroupName\",")));
        MvcResult result = this.mockMvc.perform(get("/api/users/3")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
    }

    /**
     * Test replace group group not found with coordinator.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
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

        this.mockMvc.perform(post("/api/groups/4").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":4,\"groupName\":\"newGroupName\",")));
        MvcResult result = this.mockMvc.perform(get("/api/users/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
    }

    /**
     * Test replace group change coordinator.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
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

        this.mockMvc.perform(post("/api/groups/1").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("{\"id\":1,\"groupName\":\"newGroupName2\",")));
        MvcResult result = this.mockMvc.perform(get("/api/users/2")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(stringResult.contains("\"COORDINATOR\""));
        MvcResult result2 = this.mockMvc.perform(get("/api/users/4")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult2 = result2.getResponse().getContentAsString();
        assert(!stringResult2.contains("\"COORDINATOR\""));
    }

    /**
     * Test replace group change board.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(6)
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testReplaceGroupChangeBoard() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 2,\n" +
                "  \"groupName\" : \"boardChange\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 5,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/api/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("\"board\":{\"id\":5,\"boardName\":\"testboard5\",\"location\":\"location5\"},\"coordinator\":5}")));
    }

    /**
     * Test replace group change board.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testReplaceGroupChangeBoardName() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 2,\n" +
                "  \"groupName\" : \"boardChange\",\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 5,\n" +
                "    \"boardName\" : \"changedBoardName2\",\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/api/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isOk()).andExpect(content()
                .string(containsString("\"board\":{\"id\":5,\"boardName\":\"changedBoardName2\",\"location\":\"location5\"},\"coordinator\":5}")));
        MvcResult result2 = this.mockMvc.perform(get("/api/boards/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult2 = result2.getResponse().getContentAsString();
        assert(stringResult2.contains("\"changedBoardName2\""));
    }

    /**
     * Test replace group change board.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testReplaceGroupNewGroupInsuffucientData() throws Exception {
        String requestJson = "{\n" +
                "  \"id\" : 3,\n" +
                "  \"groupName\" : null,\n" +
                "  \"users\" : [ ],\n" +
                "  \"board\" : {\n" +
                "    \"id\" : 5,\n" +
                "    \"boardName\" : null,\n" +
                "    \"location\" : null\n" +
                "  },\n" +
                "  \"coordinator\" : {\n" +
                "  \"id\" : 5 \n"+
                "  }\n"+
                "}";

        this.mockMvc.perform(post("/api/groups/90").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isInternalServerError());
    }

    /**
     * Test replace group name not unique.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
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

        this.mockMvc.perform(post("/api/groups/2").contentType(APPLICATION_JSON).content(requestJson)).andDo(print()).andExpect(status().isInternalServerError());
        MvcResult result = this.mockMvc.perform(get("/api/users/5")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert (stringResult.contains("\"COORDINATOR\""));
    }

    /**
     * Test delete group.
     *
     * @throws Exception the exception
     */
    @Test
    @Order(3)
    @WithMockUser(roles="SUPERVISOR", username = "Admin")
    public void testDeleteGroup() throws Exception {
        this.mockMvc.perform(delete("/api/groups/3").principal(SecurityContextHolder.getContext().getAuthentication())).andDo(print()).andExpect(status().isOk());
        MvcResult result = this.mockMvc.perform(get("/api/groups/")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert(!stringResult.contains("\"id\":3,\"groupName\":\"testgroup3\""));
        MvcResult result2 = this.mockMvc.perform(get("/api/users/8")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult2 = result2.getResponse().getContentAsString();
        assert(!stringResult2.contains("\"COORDINATOR\""));
    }


    /**
     * Test add user to group.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR",username="Admin")
    public void testAddUserToGroup() throws Exception {
        String request = "3";
        this.mockMvc.perform(post("/api/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).contentType(APPLICATION_JSON).content(request))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":1")));
        MvcResult result = this.mockMvc.perform(get("/api/users/1/groups")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert (stringResult.contains("\"id\":1,\"groupName\":"));
    }

    /**
     * Test add user to group without role.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAddUserToGroupWithoutRole() throws Exception {
        String request = "3";
        this.mockMvc.perform(post("/api/groups/user/1").contentType(APPLICATION_JSON).content(request))
                .andDo(print()).andExpect(status().isUnauthorized());
    }

    /**
     * Test add user to group without permission.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="USER",username="User1")
    public void testAddUserToGroupWithoutPermission() throws Exception {
        String request = "3";
        this.mockMvc.perform(post("/api/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).contentType(APPLICATION_JSON).content(request))
                .andDo(print()).andExpect(status().isForbidden());
    }

    /**
     * Test add user to group as coordinator.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="COORDINATOR",username="Coordinator1")
    public void testAddUserToGroupAsCoordinator() throws Exception {
        String request = "3";
        this.mockMvc.perform(post("/api/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).contentType(APPLICATION_JSON).content(request))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("{\"id\":1")));
        MvcResult result = this.mockMvc.perform(get("/api/users/1/groups")).andDo(print()).andExpect(status().isOk()).andReturn();
        String stringResult = result.getResponse().getContentAsString();
        assert (stringResult.contains("\"id\":1,\"groupName\":"));
    }

    /**
     * Test add user to group differnet coordinator.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="COORDINATOR",username="Coordinator2")
    public void testAddUserToGroupDiffernetCoordinator() throws Exception {
        String request = "3";
        this.mockMvc.perform(post("/api/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).contentType(APPLICATION_JSON).content(request))
                .andDo(print()).andExpect(status().isForbidden());
    }

    /**
     * Test add user to group user not found.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR",username="Admin")
    public void testAddUserToGroupUserNotFound() throws Exception {
        String request = "90";
        this.mockMvc.perform(post("/api/groups/user/1").principal(SecurityContextHolder.getContext().getAuthentication()).contentType(APPLICATION_JSON).content(request))
                .andDo(print()).andExpect(status().isNotFound());
    }

    /**
     * Test add user to group group not found.
     *
     * @throws Exception the exception
     */
    @Test
    @WithMockUser(roles="SUPERVISOR",username="Admin")
    public void testAddUserToGroupGroupNotFound() throws Exception {
        String request = "3";
        this.mockMvc.perform(post("/api/groups/user/90").principal(SecurityContextHolder.getContext().getAuthentication()).contentType(APPLICATION_JSON).content(request))
                .andDo(print()).andExpect(status().isNotFound());
    }
}
