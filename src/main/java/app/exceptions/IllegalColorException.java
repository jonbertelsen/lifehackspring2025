package app.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IllegalColorException extends RuntimeException {
    private static final Logger LOGGER = Logger.getLogger(IllegalColorException.class.getName());

    public IllegalColorException(String userMessage)
    {
        super(userMessage);
        LOGGER.log(Level.WARNING, userMessage);
    }

    public IllegalColorException(String userMessage, String systemMessage) {
        super(userMessage);
        LOGGER.log(Level.WARNING, userMessage);
        LOGGER.log(Level.WARNING, "errorMessage: " + systemMessage);
    }
}
