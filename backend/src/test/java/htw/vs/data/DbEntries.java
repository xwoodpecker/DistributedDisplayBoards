package htw.vs.data;

public class DbEntries {


    //boards
    public static final Long Board1_Id = 1L;
    public static final Long Board2_Id = 2L;

    public static final String Board1_BoardName = "testboard1";
    public static final String Board2_BoardName = "testboard2";

    //users
    public static final long User1_Id = 1L;
    public static final long User2_Id = 2L;
    public static final long User3_Id = 3L;
    public static final long Coordinator1_Id = 4L;
    public static final long Coordinator2_Id = 5L;
    public static final long Supervisor_Id = 6L;

    public static final String User1_Username = "User1";
    public static final String User2_Username = "User2";
    public static final String User3_Username = "User3";
    public static final String Coordinator1_Username = "Coordinator1";
    public static final String Coordinator2_Username = "Coordinator2";
    public static final  String Supervisor_Username = "Admin";

    //groups
    public static final long BoardGroup1_ID = 1L;
    public static final long BoardGroup2_ID = 2L;

    public static final String BoardGroup1_GroupName = "testgroup1";
    public static final String BoardGroup2_GroupName = "testgroup2";

    //messages
    public static final Integer MessagesToDisplay = 2;

    //Roles
    //TODO: david pls fix your CONST.java and refactor this
    public static final String SUPERVISOR_ROLE = "SUPERVISOR";
    public static final String COORDINATOR_ROLE = "COORDINATOR";
    public static final String USER_ROLE = "USER";

}
