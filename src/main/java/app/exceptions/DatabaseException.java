package app.exceptions;

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