package app.exceptions;

import java.sql.SQLException;

public class Team03DatabaseException extends Throwable {
    public Team03DatabaseException(SQLException ex, String s) {
        System.out.println(ex.getMessage());
    }
}
