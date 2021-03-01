package htw.vs.base;

public class Const {

    //Exceptions
    public static final String VERIFY_USER_EXCEPTION = "UserName and authentication do not match";
    public static final String USER_NOT_FOUND_EXCEPTION = "User not found";
    public static final String PUSH_MESSAGE_EXCEPTION = "Referenced Board is not the central Board";
    public static final String USER_BOARD_EXCEPTION = "User is not assigned to this board";

    //Roles
    public static final String SUPERVISOR_ROLE = "SUPERVISOR";
    public static final String COORDINATOR_ROLE = "COORDINATOR";
    public static final String USER_ROLE = "USER";

    //HTML Responses
    public static final String NO_GROUP_MSG = "No group found";
    public static final String NO_BOARD_MSG = "No boards found";
    public static final String BOARD_NOT_FOUND_MSG = "Board not found";
    public static final String USER_NOT_FOUND_MSG = "User not found";

}
