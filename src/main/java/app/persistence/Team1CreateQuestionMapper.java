package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team1CreateQuestionMapper {

    public static void createQuestion(ConnectionPool connectionPool, String categoryName, Team1Entities.Questions question) throws DatabaseException {
        String sql = "INSERT INTO questions (question, answer, point)\n" +
                "VALUES (?, ?, ?);";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int rowsCreated = ps.executeUpdate();

            ps.setString(1, question.getQuestion());
            ps.setString(2, question.getAnswer());
            ps.setInt(3, question.getId());

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not insert into questions");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not insert question into database", ex.getMessage());
        }
    }
}