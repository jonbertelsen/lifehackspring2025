package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team1QuestionMapper {

    public static void createQuestion(ConnectionPool connectionPool, Team1Entities.Questions questions, Team1Entities.Categories category) throws DatabaseException {
        //A method which can create a question, answer, point with a category id
        String sql = "INSERT INTO lifehack_team_1_questions (question, answer, point, category_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, questions.getQuestion());
            ps.setString(2, questions.getAnswer());
            ps.setInt(3, questions.getPoints());
            ps.setInt(4, category.getId());

            int rowsCreated = ps.executeUpdate();

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not create question with answer");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not create question in database", ex.getMessage());
        }
    }

    public static void updateQuestion(ConnectionPool connectionPool, Team1Entities.Questions questions) throws DatabaseException {
        //A method which can update the question
        String sql = "UPDATE lifehack_team_1_questions SET question = ?, answer = ?, point = ?  WHERE id = ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, questions.getQuestion());
            ps.setString(2, questions.getAnswer());
            ps.setInt(3, questions.getPoints());
            ps.setInt(4, questions.getId());

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated == 0) {
                throw new DatabaseException("Could not update answer for question ID: " + questions.getId());
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Error updating answer in database", ex.getMessage());
        }
    }

    public static List<Team1Entities.Questions> listOfQuestions(ConnectionPool connectionPool, int id) throws DatabaseException{
        //A method that returns a list of questions with a category id
        List<Team1Entities.Questions> questionsList = new ArrayList<>();
        String sql = "SELECT * FROM lifehack_team_1_questions WHERE category_id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int question_id = rs.getInt("id");
                    String question = rs.getString("question");
                    String answer = rs.getString("answer");
                    int point = rs.getInt("point");
                    Team1Entities.Questions questions = new Team1Entities.Questions(question_id, question, answer, point);
                    questionsList.add(questions);
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return questionsList;
    }

    public static Team1Entities.Questions getQuestionById(ConnectionPool connectionPool, int id )throws DatabaseException{
        //A method that returns a list of questions with a category id
        String sql = "SELECT * FROM lifehack_team_1_questions WHERE id = ?";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    int question_id = rs.getInt("id");
                    String question = rs.getString("question");
                    String answer = rs.getString("answer");
                    int point = rs.getInt("point");
                    Team1Entities.Questions questions = new Team1Entities.Questions(question_id, question, answer, point);
                    return questions;
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return null;
    }
}