package app.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class IllegalSpeciesException extends RuntimeException {
    private static final Logger LOGGER = Logger.getLogger(IllegalSpeciesException.class.getName());

    public IllegalSpeciesException(String userMessage)
    {
        super(userMessage);
        LOGGER.log(Level.WARNING, userMessage);
    }

    public IllegalSpeciesException(String userMessage, String systemMessage) {
        super(userMessage);
        LOGGER.log(Level.WARNING, userMessage);
        LOGGER.log(Level.WARNING, "errorMessage: " + systemMessage);
    }
}
