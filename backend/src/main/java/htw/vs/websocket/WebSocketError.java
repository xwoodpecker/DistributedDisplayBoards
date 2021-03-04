package htw.vs.websocket;

/**
 * The type Web socket error.
 */
public class WebSocketError {
        private String code;
        private String message;

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets code.
     *
     * @param code the code
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Instantiates a new Web socket error.
     *
     * @param code    the code
     * @param message the message
     */
    public WebSocketError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
