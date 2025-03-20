package app.persistence;

import app.exceptions.LifeHackTeam3DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LifeHackTeam3SubscriberMapper {


    public void addNewSubscriber(ConnectionPool connectionPool, String email, String username, String password) throws LifeHackTeam3DatabaseException {
        String sql = "INSERT INTO users_lifehack_team_3 (user_email, user_name, user_password) " +
                "VALUES (?, ?, ?) " +
                "ON CONFLICT (user_email) DO NOTHING;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, username);
                ps.setString(3, password);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("User already exists or insert failed.");
                }
            }
        } catch (SQLException ex) {
            throw new LifeHackTeam3DatabaseException(ex, "Could not get users from database");
        }
    }


    public int getUserIdFromEmail(ConnectionPool connectionPool, String email) throws LifeHackTeam3DatabaseException {
        String sql = "SELECT user_id FROM users_lifehack_team_3 WHERE user_email = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (rs.getInt("user_id"));

                } else {
                    throw new LifeHackTeam3DatabaseException(null, "Could not get user id from database");
                }
            }
        } catch (SQLException ex) {
            throw new LifeHackTeam3DatabaseException(ex, "Could not get user from database");
        }
    }

    public void addSubscriberToReminder(ConnectionPool connectionPool, int reminder_id, int mailIdFromEmail) throws
            LifeHackTeam3DatabaseException {

        String sql = "INSERT INTO reminder_subscriber_lifehack_team_3 (reminder_id, mail_id) VALUES (?, ?);";


        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, reminder_id);
                ps.setInt(2, mailIdFromEmail);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("User already exists or insert failed.");
                }
            }
        } catch (SQLException ex) {
            throw new LifeHackTeam3DatabaseException(ex, "Could not get users from database");
        }
    }

    public void removeSubscriberFromReminder(ConnectionPool connectionPool, int reminder_id, int userId) throws LifeHackTeam3DatabaseException {
        String sql = "DELETE FROM reminder_subscriber_lifehack_team_3 WHERE reminder_id = ? AND mail_id = ?;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, reminder_id);
                ps.setInt(2, userId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("No subscription found to remove.");
                } else {
                    System.out.println("Subscriber removed from reminder.");
                }
            }
        } catch (SQLException ex) {
            throw new LifeHackTeam3DatabaseException(ex, "Could not remove subscriber from reminder.");
        }
    }


    // De to metoder der kører koden
    public void executeRemoveSubscriberFromReminder(ConnectionPool connectionPool, String email, int reminder_id) throws LifeHackTeam3DatabaseException {
        int userId = getUserIdFromEmail(connectionPool, email);
        removeSubscriberFromReminder(connectionPool, reminder_id, userId);
    }

    public void executeAddSubscriberToReminder(ConnectionPool connectionPool, String email, int reminder_id) throws LifeHackTeam3DatabaseException {
        int userId = getUserIdFromEmail(connectionPool, email);
        addSubscriberToReminder(connectionPool, reminder_id, userId);
    }
}
