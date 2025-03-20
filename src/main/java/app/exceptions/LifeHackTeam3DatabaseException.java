package app.exceptions;

import java.sql.SQLException;

public class LifeHackTeam3DatabaseException extends Throwable {
    public LifeHackTeam3DatabaseException(SQLException ex, String s) {
        System.out.println(ex.getMessage());
    }
}
