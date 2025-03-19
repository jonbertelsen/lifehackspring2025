package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team1QuestionMapper {

    public static void createAnswer(ConnectionPool connectionPool, Team1Entities.Questions questions, Team1Entities.Categories category) throws DatabaseException {
        String sql = "INSERT INTO lifehack_team_1_questions (question, answer, point, category_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {


            ps.setString(1, questions.getQuestion());
            ps.setString(2, questions.getAnswer());
            ps.setInt(3,questions.getPoints());
            ps.setInt(4, category.getId());

            int rowsCreated = ps.executeUpdate();

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not create question with answer");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not create question in database", ex.getMessage());
        }
    }

    public static void updateQuestion(ConnectionPool connectionPool, Team1Entities.Questions questions, Team1Entities.Categories category) throws DatabaseException {
        String sql = "UPDATE lifehack_team_1_questions SET answer = ? WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1,questions.getPoints());
            ps.setString(2, questions.getQuestion());
            ps.setString(3, questions.getAnswer());
            ps.setInt(4, category.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DatabaseException("Could not update answer for question ID: " + questions.getId());
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error updating answer in database", ex.getMessage());
        }
    }
}