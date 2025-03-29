package app.persistence.team08;

import app.entities.team08.Team08User;
import app.persistence.ConnectionPool;

import java.sql.*;

public class Team08UserMapper {

    private final ConnectionPool connectionPool;

    public Team08UserMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void createUser(String username, String password) {
        String sql = "INSERT INTO lifehack_team_08_users (username, password, eggs) VALUES (?, ?, 0)";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Team08User getUserByUsername(String username) {
        String sql = "SELECT * FROM lifehack_team_08_users WHERE username = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Found user: " + rs.getString("username"));
                return new Team08User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBigDecimal("eggs").toBigInteger(),
                        rs.getInt("chicken_feed_tier"),
                        rs.getInt("predator_tier")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Team05User not found
    }


    public Team08User getUserById(int userId) {
        String sql = "SELECT * FROM lifehack_team_08_users WHERE user_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Team08User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBigDecimal("eggs").toBigInteger(),
                        rs.getInt("chicken_feed_tier"),
                        rs.getInt("predator_tier")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Team05User not found
    }

    public void updateUser(Team08User user) {
        String sql = "UPDATE lifehack_team_08_users SET eggs = ?, chicken_feed_tier = ?, predator_tier = ? WHERE user_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, new java.math.BigDecimal(user.getEggs())); // Convert BigInteger to BigDecimal
            stmt.setInt(2, user.getChickenFeedTier());
            stmt.setInt(3, user.getPredatorTier());
            stmt.setInt(4, user.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
