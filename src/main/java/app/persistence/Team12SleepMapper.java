package app.persistence;

import app.exceptions.Team12DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team12SleepMapper {

    public static void saveSleepData(int userId,String sleepStart, String sleepEnd, String sleepDuration, ConnectionPool connectionPool) throws Team12DatabaseException {
        String sql = "insert into team12_sleep_records (sleep_start, sleep_end, sleep_date) values (?,?,?)";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ps.setInt(1,userId);
            ps.setString(1, sleepStart);
            ps.setString(2, sleepEnd);
            ps.setString(3, sleepDuration);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected != 1) {
                throw new Team12DatabaseException("Fejl ved indtastning af søvn");
            }
        } catch (SQLException e) {
            String msg = "Der er sket en fejl. Prøv igen";
            if (e.getMessage().startsWith("ERROR: duplicate key value ")) {
                msg = "Tisse trold 47";
            }
            throw new Team12DatabaseException(msg, e.getMessage());
        }
    }
}
