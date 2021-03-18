package htw.vs.rest;

import java.util.List;

/**
 * The type Error response.
 */
public class ErrorResponse
{
    /**
     * Instantiates a new Error response.
     *
     * @param message the message
     * @param details the details
     */
    public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }

    private String message;
    private List<String> details;

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
     * Gets details.
     *
     * @return the details
     */
    public List<String> getDetails() {
        return details;
    }

    /**
     * Sets details.
     *
     * @param details the details
     */
    public void setDetails(List<String> details) {
        this.details = details;
    }
}