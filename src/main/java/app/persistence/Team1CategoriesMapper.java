package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team1CategoriesMapper {
    //A method which can create categories
    public static void createCategory(ConnectionPool connectionPool, Team1Entities.Categories categories, Team1Entities.Quiz quiz) throws DatabaseException {
        String sql = "INSERT INTO lifehack_team_1_categories (category_name, quiz_id) VALUES (?, ?);";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, categories.getCategoryName());
            ps.setInt(2, quiz.getId());

            int rowsCreated = ps.executeUpdate();

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not create category name");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not create category into the database", ex.getMessage());
        }
    }

    //A method which can create categories
    public static void updateCategory(ConnectionPool connectionPool, Team1Entities.Categories categories, Team1Entities.Quiz quiz) throws DatabaseException {
        //A method which can update a category
        String sql = "UPDATE lifehack_team_1_categories SET category_name = ? WHERE id = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, categories.getCategoryName());
            ps.setInt(2, quiz.getId());

            int rowsCreated = ps.executeUpdate();

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not update the category");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not update category in the database", ex.getMessage());
        }
    }

    public static List<Team1Entities.Categories> categories(ConnectionPool connectionPool) throws DatabaseException{
        List<Team1Entities.Categories> categoriesList = new ArrayList<>();
        String sql = "SELECT * FROM * Categories";

        try(Connection connection = connectionPool.getConnection()){
            try try (PreparedStatement ps = connection.prepareStatement(sql)){
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    int quizId = rs.getInt("quiz_id");
                    String categoryName = rs.getString("category_name");
                    Team1Entities.Categories categories = new Team1Entities.Categories(quizId, categoryName);
                    //TODO: Spørg Thomas om vi skal lave en ny constructor i entitites for at kunne hente data ned fra db.
                }
            }
        }
    }
}
