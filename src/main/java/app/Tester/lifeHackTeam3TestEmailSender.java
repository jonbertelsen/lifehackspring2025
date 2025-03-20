package app.Tester;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class lifeHackTeam3TestEmailSender {
    private static final String SMTP_HOST = "smtp.gmail.com"; // Din SMTP-server
    private static final String SMTP_USER = "lifehackspring2025team3@gmail.com";
    private static final String SMTP_PASSWORD = "chuf kdvn rspe svkt"; // App password

    public static void main(String[] args) {
        // Udskift med din egen email for at teste
        String testRecipient = "jonas.outzen.j@gmail.com";
        String subject = "Test af Mail Reminder Service";
        String messageText = "Dette er en testmail sendt fra TestEmailSender.";

        sendTestEmail(testRecipient, subject, messageText);
    }

    public static void sendTestEmail(String to, String subject, String text) {
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
            System.out.println("Email sendt til: " + to);
        } catch (MessagingException e) {
            System.err.println("Fejl under afsendelse af email:");
            e.printStackTrace();
        }
    }
}
