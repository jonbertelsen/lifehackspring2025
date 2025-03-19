package app.persistence;

import app.entities.Team1Entities;
import app.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Team1UpdateCategoriesMapper {

    public static void updateCategory(ConnectionPool connectionPool, Team1Entities.Categories categories) throws DatabaseException, SQLException{
        String sql = "UPDATE categories \n" +
                "SET category_name = (?)\n" +
                "WHERE id = (?);";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, categories.getCategoryName());
                ps.setInt(2, categories.getId());

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated == 0) {
                    throw new DatabaseException("Could not update category name");
                }
            } catch (SQLException ex) {
                throw new DatabaseException("Could not get users from database", ex.getMessage());
            }
        }
    }
}