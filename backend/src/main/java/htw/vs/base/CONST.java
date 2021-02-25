package htw.vs.base;

public class CONST {
    //Config
    public static final String BASIC_TOPIC = "/topic/boards.";
    public static final String DEFAULT_SUPERVISOR_PASSWORD = "supervisor";
    public static final String CENTRAL_BOARD_NAME = "central";
    public static final String BROKER_HOST = "v4.deuersprech.de";
    public static final int BROKER_PORT = 61613;
    public static final String BROKER_LOGIN = "guest";
    public static final String BROKER_PASSCODE = "guest";
    public static final Long CENTRAL_BOARD_ID = 42L;

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
    public static final String BOARD_NOT_FOUND_MSG = "Board not found";
    public static final String USER_NOT_FOUND_MSG = "User not found";

}
