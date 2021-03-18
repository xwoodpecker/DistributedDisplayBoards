package htw.vs.base;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

/**
 * The type Config.
 */
public class Config {
    /**
     * The constant BASIC_TOPIC.
     */
    public static final String BASIC_TOPIC;
    /**
     * The constant DEFAULT_SUPERVISOR_PASSWORD.
     */
    public static final String DEFAULT_SUPERVISOR_PASSWORD;
    /**
     * The constant CENTRAL_BOARD_NAME.
     */
    public static final String CENTRAL_BOARD_NAME;
    /**
     * The constant CENTRAL_GROUP_NAME.
     */
    public static final String CENTRAL_GROUP_NAME;
    /**
     * The constant BROKER_HOST.
     */
    public static final String BROKER_HOST;
    /**
     * The constant BROKER_PORT.
     */
    public static final int BROKER_PORT;
    /**
     * The constant BROKER_LOGIN.
     */
    public static final String BROKER_LOGIN;
    /**
     * The constant BROKER_PASSCODE.
     */
    public static final String BROKER_PASSCODE;

    private Config() { }

    static {
        URL root = Config.class.getProtectionDomain().getCodeSource().getLocation();
        URL propertiesFile = null;
        try {
            propertiesFile = new URL(root, "application.properties");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(propertiesFile.openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        BASIC_TOPIC = properties.getProperty("BASIC_TOPIC");
        DEFAULT_SUPERVISOR_PASSWORD = properties.getProperty("DEFAULT_SUPERVISOR_PASSWORD");
        CENTRAL_BOARD_NAME = properties.getProperty("CENTRAL_BOARD_NAME");
        CENTRAL_GROUP_NAME = properties.getProperty("CENTRAL_GROUP_NAME");
        BROKER_HOST = properties.getProperty("BROKER_HOST");
        BROKER_PORT = Integer.parseInt(properties.getProperty("BROKER_PORT"));
        BROKER_LOGIN = properties.getProperty("BROKER_LOGIN");
        BROKER_PASSCODE = properties.getProperty("BROKER_PASSCODE");

        System.out.println("BROKER_HOST");
        System.out.println(BROKER_HOST);
    }
}
