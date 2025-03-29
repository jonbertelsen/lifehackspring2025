package app.exceptions;

public class Team12DatabaseException extends Throwable {

    public Team12DatabaseException(String userMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
    }

    public Team12DatabaseException(String userMessage, String systemMessage) {
        super(userMessage);
        System.out.println("userMessage: " + userMessage);
        System.out.println("errorMessage: " + systemMessage);
    }
}
