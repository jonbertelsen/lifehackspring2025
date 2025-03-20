package app.persistence;

import app.entities.LifehackTeam08User;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LifehackTeam08UserMapper {

    private final ConnectionPool connectionPool;

    public LifehackTeam08UserMapper(ConnectionPool connectionPool) {
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

    public LifehackTeam08User getUserByUsername(String username) {
        String sql = "SELECT * FROM lifehack_team_08_users WHERE username = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Found user: " + rs.getString("username"));
                return new LifehackTeam08User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),  // No hashing, just store it directly
                        rs.getBigDecimal("eggs").toBigInteger(),
                        rs.getInt("chicken_feed_tier"),
                        rs.getInt("predator_tier")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // User not found
    }


    public void updateEggs(int userId, BigInteger newEggCount) {
        String sql = "UPDATE lifehack_team_08_users SET eggs = ? WHERE user_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, newEggCount);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public LifehackTeam08User getUserById(int userId) {
        String sql = "SELECT * FROM lifehack_team_08_users WHERE user_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new LifehackTeam08User(
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
        return null; // User not found
    }

    public void updateUserStats(int userId, BigInteger eggs, int chickenFeedTier, int predatorTier) {
        String sql = "UPDATE lifehack_team_08_users SET eggs = ?, chicken_feed_tier = ?, predator_tier = ? WHERE user_id = ?";
        System.out.println("Updating User Stats: ID=" + userId + ", Eggs=" + eggs + ", Chicken Feed Tier=" + chickenFeedTier + ", Predator Tier=" + predatorTier);

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, eggs);
            stmt.setInt(2, chickenFeedTier);
            stmt.setInt(3, predatorTier);
            stmt.setInt(4, userId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Database updated successfully!");
            } else {
                System.out.println("❌ ERROR: No rows updated! Check if user exists.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ SQL Error: " + e.getMessage());
        }
    }


    public void updateChickenFeedTier(int userId, int chickenFeedTier){
        String sql = "UPDATE lifehack_team_08_users SET chicken_feed_tier = ? WHERE user_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chickenFeedTier);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePredatorTier(int userId, int predatorTier) {
        String sql = "UPDATE lifehack_team_08_users SET predator_tier = ? WHERE user_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, predatorTier);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LifehackTeam08User> getAllUsers() {
        List<LifehackTeam08User> users = new ArrayList<>();
        String sql = "SELECT * FROM lifehack_team_08_users";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LifehackTeam08User user = new LifehackTeam08User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getBigDecimal("eggs").toBigInteger(),
                        rs.getInt("chicken_feed_tier"),
                        rs.getInt("predator_tier")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

}
