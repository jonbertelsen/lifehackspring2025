package app.persistence;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class LifeHackTeam3MailSender {
    private static final String SMTP_HOST = "smtp.gmail.com"; // Udskift med din SMTP-server
    private static final String SMTP_USER = "lifehackspring2025team3@gmail.com";
    private static final String SMTP_PASSWORD = "chuf kdvn rspe svkt"; // app password
    // mail password "lifehackteam3";


    private final ConnectionPool connectionPool;

    public LifeHackTeam3MailSender(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public void sendReminders() {
        List<String> emails = getAllEmails();
        for (String email : emails) {
            List<String> reminders = getUserReminders(email);
            for (String reminder : reminders) {
                List<String> messages = getReminderMessages(reminder);
                if (!messages.isEmpty()) {
                    String message = getRandomMessage(messages);
                    sendEmail(email, "Din daglige reminder!", message);
                }
            }
        }
    }

    private List<String> getAllEmails() {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT DISTINCT email FROM mail_info_lifehack_team_3";

        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                emails.add(rs.getString("email"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    private List<String> getUserReminders(String email) {
        List<String> reminders = new ArrayList<>();
        String sql = "SELECT r.reminder_name " + "FROM mail_info_lifehack_team_3 mi " + "JOIN reminder_subscriber_lifehack_team_3 rs ON mi.mail_id = rs.mail_id " + "JOIN reminders_lifehack_team_3 r ON rs.reminder_id = r.reminder_id " + "WHERE mi.email = ?";

        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reminders.add(rs.getString("reminder_name"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reminders;
    }

    private List<String> getReminderMessages(String reminderName) {
        List<String> messages = new ArrayList<>();
        String sql = "SELECT d.description " + "FROM reminders_lifehack_team_3 r " + "JOIN reminders_description_lifehack_team_3 d ON r.reminder_id = d.reminder_id " + "WHERE r.reminder_name = ?";

        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reminderName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    messages.add(rs.getString("description"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    private String getRandomMessage(List<String> messages) {
        Random random = new Random();
        return messages.get(random.nextInt(messages.size()));
    }

    private void sendEmail(String to, String subject, String text) {
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SMTP_USER, SMTP_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTP_USER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

