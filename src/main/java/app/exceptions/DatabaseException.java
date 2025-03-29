package app.exceptions;

<<<<<<< HEAD
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseException extends Exception {
    private static final Logger LOGGER = Logger.getLogger(DatabaseException.class.getName());

    public DatabaseException(String userMessage) {
        super(userMessage);
        LOGGER.log(Level.WARNING, userMessage);
    }

    public DatabaseException(String userMessage, String systemMessage) {
        super(userMessage);
        LOGGER.log(Level.WARNING, userMessage);
        LOGGER.log(Level.WARNING, "errorMessage: " + systemMessage);
    }
}
=======
import java.sql.SQLException;

public class DatabaseException extends Exception {
  public DatabaseException(SQLException cause, String message) {
    super(message, cause);
  }

  public DatabaseException(String message) {
    super(message);
  }

    public DatabaseException(String dbFejl, String message) {
        super(message + dbFejl );
    }
}
>>>>>>> main
