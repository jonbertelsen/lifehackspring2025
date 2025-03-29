package app.persistence.team03;

import app.persistence.ConnectionPool;
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

public class Team03MailSender {
    private static final String SMTP_HOST = "smtp.gmail.com"; // Din SMTP-server
    private static final String SMTP_USER = "lifehackspring2025team3@gmail.com";
    private static final String SMTP_PASSWORD = "chuf kdvn rspe svkt"; // app password

    private final ConnectionPool connectionPool;

    public Team03MailSender(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    /**
     * Sender påmindelser til alle brugere (hentet fra users-tabellen).
     */
    public void sendReminders() {
        List<String> emails = getAllEmails();
        System.out.println("[sendReminders] Fundne emails: " + emails);
        for (String email : emails) {
            sendReminderForUser(email);
        }
    }

    /**
     * Henter alle unikke bruger-emailadresser fra users-tabellen.
     */
    private List<String> getAllEmails() {
        List<String> emails = new ArrayList<>();
        String sql = "SELECT DISTINCT user_email FROM users_lifehack_team_3";

        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String email = rs.getString("user_email");
                emails.add(email);
            }
        } catch (SQLException e) {
            System.err.println("[getAllEmails] Fejl: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("[getAllEmails] Returnerer: " + emails);
        return emails;
    }

    /**
     * Henter de påmindelser, som en given bruger er tilmeldt.
     * Vi antager, at der findes en mapping-tabel 'reminder_subscriber_lifehack_team_3'
     * som bruger user_id fra users-tabellen.
     */
    private List<String> getUserReminders(String email) {
        List<String> reminders = new ArrayList<>();
        String sql = "SELECT r.reminder_name " + "FROM users_lifehack_team_3 u " + "JOIN reminder_subscriber_lifehack_team_3 rs ON u.user_id = rs.mail_id " + "JOIN reminders_lifehack_team_3 r ON rs.reminder_id = r.reminder_id " + "WHERE u.user_email = ?";

        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reminders.add(rs.getString("reminder_name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("[getUserReminders] Fejl for email " + email + ": " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("[getUserReminders] Reminders for " + email + ": " + reminders);
        return reminders;
    }


    /**
     * Henter de beskeder (descriptions), der hører til en given reminder.
     */
    private List<String> getReminderMessages(String reminderName) {
        List<String> messages = new ArrayList<>();
        String sql = "SELECT d.description " + "FROM reminders_lifehack_team_3 r " + "JOIN reminders_description_lifehack_team_3 d ON r.reminder_id = d.reminder_id " + "WHERE r.reminder_name = ?";

        try (Connection conn = connectionPool.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, reminderName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String description = rs.getString("description");
                    messages.add(description);
                }
            }
        } catch (SQLException e) {
            System.err.println("[getReminderMessages] Fejl for reminder " + reminderName + ": " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("[getReminderMessages] Beskeder for " + reminderName + ": " + messages);
        return messages;
    }

    private String getRandomMessage(List<String> messages) {
        Random random = new Random();
        return messages.get(random.nextInt(messages.size()));
    }

    /**
     * Afsender en email via SMTP.
     */
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
            System.out.println("[sendEmail] Email sendt til: " + to);
        } catch (MessagingException e) {
            System.err.println("[sendEmail] Fejl under afsendelse til " + to + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sender påmindelser til en enkelt bruger baseret på brugerens email.
     */
    public void sendReminderForUser(String email) {
        System.out.println("[sendReminderForUser] Sender reminder for bruger: " + email);
        List<String> reminders = getUserReminders(email);
        if (reminders.isEmpty()) {
            System.out.println("[sendReminderForUser] Ingen reminders fundet for " + email);
        }
        for (String reminder : reminders) {
            List<String> messages = getReminderMessages(reminder);
            if (!messages.isEmpty()) {
                String message = getRandomMessage(messages);
                System.out.println("[sendReminderForUser] Sender reminder email til " + email + " med besked: " + message);
                sendEmail(email, "Din daglige reminder!", message);
            } else {
                System.out.println("[sendReminderForUser] Ingen beskeder fundet for reminder: " + reminder);
            }
        }
    }
}
