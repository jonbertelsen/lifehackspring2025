package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team1CreateCategoriesMapper {

    public static void createCategory(ConnectionPool connectionPool, Team1Entities.Categories categories) throws DatabaseException {
        String sql = "INSERT INTO categories (category_name) VALUES (?);";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            int rowsCreated = ps.executeUpdate();

            ps.setString(1, categories.getCategoryName());

            if (rowsCreated == 0) {
                throw new DatabaseException("Could not insert category name");
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Could not insert category into database", ex.getMessage());
        }
    }
}