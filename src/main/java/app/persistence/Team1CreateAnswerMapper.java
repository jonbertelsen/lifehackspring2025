package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team1CreateAnswerMapper {

    public static void updateQuestion(ConnectionPool connectionPool, Team1Entities.Questions questions) throws DatabaseException {
        String sql = "INSERT INTO questions\n" +
                "VALUES answer = (?)\n";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int rowsCreated = ps.executeUpdate();

            ps.setString(1, questions.getAnswer());

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not update questions");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not update question into database", ex.getMessage());
        }
    }
}