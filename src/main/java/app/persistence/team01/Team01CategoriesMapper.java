package app.persistence.team01;

import app.entities.team01.Team01Entity;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Team01CategoriesMapper {
    //A method which can create categories
    public static void createCategory(ConnectionPool connectionPool, Team01Entity.Categories categories, Team01Entity.Quiz quiz) throws DatabaseException {
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

    public static void updateCategory(ConnectionPool connectionPool, Team01Entity.Categories categories, Team01Entity.Quiz quiz) throws DatabaseException {
        //A method which can update a category
        String sql = "UPDATE lifehack_team_1_categories SET category_name = ? WHERE id = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, categories.getCategoryName());
            ps.setInt(2, quiz.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Could not update the category");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not update category in the database", ex.getMessage());
        }
    }

    public static List<Team01Entity.Categories> listOfCategories(ConnectionPool connectionPool, int id) throws DatabaseException, SQLException {
        //A method which returns a list of categories with a quiz id
        List<Team01Entity.Categories> categoriesList = new ArrayList<>();
        String sql = "SELECT * FROM lifehack_team_1_categories WHERE quiz_id = ?";

        try(Connection connection = connectionPool.getConnection()){
            try (PreparedStatement ps = connection.prepareStatement(sql)){
                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    int category_id = rs.getInt("id");
                    String categoryName = rs.getString("category_name");
                    Team01QuestionMapper questionMapper = new Team01QuestionMapper();
                    List<Team01Entity.Questions> questions = questionMapper.listOfQuestions(connectionPool, category_id);
                    Team01Entity.Categories categories = new Team01Entity.Categories(category_id, categoryName, questions);
                    categoriesList.add(categories);
                }
            }
        }
        return categoriesList;
    }
}