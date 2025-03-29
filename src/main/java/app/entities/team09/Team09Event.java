package app.entities.team09;

public class Team09Event {
    private int eventId;
    private String title;
    private String description;
    private String dateAndTime;
    private String hostName;

    // Konstruktør med eventId (bruges når event allerede har et ID)
    public Team09Event(int eventId, String title, String description, String dateAndTime, String hostName) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.hostName = hostName;
    }

    // Konstruktør UDEN eventId (bruges når vi opretter et nyt event)
    public Team09Event(String title, String description, String dateAndTime, String hostName) {
        this(0, title, description, dateAndTime, hostName); // Sætter eventId til 0 (placeholder)
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() { return eventId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDateAndTime() { return dateAndTime; }
    public String getHostName() { return hostName; }
}
