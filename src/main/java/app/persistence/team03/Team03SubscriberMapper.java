package app.persistence.team03;

import app.exceptions.Team03DatabaseException;
import app.persistence.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Team03SubscriberMapper {


    public void addNewUser(ConnectionPool connectionPool, String email, String username, String password) throws Team03DatabaseException {
        String sql = "INSERT INTO users_lifehack_team_3 (user_email, user_name, user_password) " + "VALUES (?, ?, ?) " + "ON CONFLICT (user_email) DO NOTHING;";

        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, username);
                ps.setString(3, password);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Team05User already exists or insert failed.");
                }
            }
        } catch (SQLException ex) {
            throw new Team03DatabaseException(ex, "Could not get users from database");
        }
    }


    public int getUserIdFromEmail(ConnectionPool connectionPool, String email) throws Team03DatabaseException {
        String sql = "SELECT user_id FROM users_lifehack_team_3 WHERE user_email = ?;";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (rs.getInt("user_id"));

                } else {
                    throw new Team03DatabaseException(null, "Could not get user id from database");
                }
            }
        } catch (SQLException ex) {
            throw new Team03DatabaseException(ex, "Could not get user from database");
        }
    }

    public int getReminderIdFromReminderName(ConnectionPool connectionPool, String reminderName) throws Team03DatabaseException {
        String sql = "SELECT reminder_id FROM reminders_lifehack_team_3 WHERE reminder_name = ?;";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, reminderName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return (rs.getInt("reminder_id"));

                } else {
                    throw new Team03DatabaseException(null, "Could not get user id from database");
                }
            }
        } catch (SQLException ex) {
            throw new Team03DatabaseException(ex, "Could not get user from database");
        }
    }


    public void addSubscriberToReminder(ConnectionPool connectionPool, int reminder_id, int mailIdFromEmail) throws Team03DatabaseException {

        String sql = "INSERT INTO reminder_subscriber_lifehack_team_3 (reminder_id, mail_id) VALUES (?, ?);";


        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, reminder_id);
                ps.setInt(2, mailIdFromEmail);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 0) {
                    System.out.println("Team05User already exists or insert failed.");
                }
            }
        } catch (SQLException ex) {
            throw new Team03DatabaseException(ex, "Could not get users from database");
        }
    }

    public void removeSubscriberFromReminder(ConnectionPool connectionPool, int reminder_id, int userId) throws Team03DatabaseException {
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
            throw new Team03DatabaseException(ex, "Could not remove subscriber from reminder.");
        }
    }

    public String getEmailFromUsername(ConnectionPool connectionPool, String username) throws Team03DatabaseException {
        String sql = "SELECT user_email FROM users_lifehack_team_3 WHERE user_name = ?;";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("user_email");
                } else {
                    throw new Team03DatabaseException(null, "Could not find email for the given username.");
                }
            }
        } catch (SQLException ex) {
            throw new Team03DatabaseException(ex, "Could not fetch email from database.");
        }
    }


    // De to metoder der kører koden
    public void executeRemoveSubscriberFromReminder(ConnectionPool connectionPool, String email, String reminderName) throws Team03DatabaseException {
        int userId = getUserIdFromEmail(connectionPool, email);
        int reminderId = getReminderIdFromReminderName(connectionPool, reminderName);
        removeSubscriberFromReminder(connectionPool, reminderId, userId);
    }

    public void executeAddSubscriberToReminder(ConnectionPool connectionPool, String email, String reminderName) throws Team03DatabaseException {
        int userId = getUserIdFromEmail(connectionPool, email);
        int reminderId = getReminderIdFromReminderName(connectionPool, reminderName);
        addSubscriberToReminder(connectionPool, reminderId, userId);
    }

    public boolean verifyUserCredentials(ConnectionPool connectionPool, String username, String password) throws Team03DatabaseException {
        String sql = "SELECT user_id FROM users_lifehack_team_3 WHERE user_name = ? AND user_password = ?;";
        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            throw new Team03DatabaseException(ex, "Could not verify user credentials");
        }
    }


}
