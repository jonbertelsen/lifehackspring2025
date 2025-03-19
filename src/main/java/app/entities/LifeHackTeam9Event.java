package app.entities;

public class LifeHackTeam9Event {
    private int eventId;
    private String title;
    private String description;
    private String dateAndTime;
    private String hostName;


    public LifeHackTeam9Event(int eventId, String title, String description, String dateAndTime, String hostName) {
        this.eventId = eventId;
        this.title = title;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.hostName = hostName;
    }

    public LifeHackTeam9Event(String title, String description, String dateAndTime, String hostName) {
        this.title = title;
        this.description = description;
        this.dateAndTime = dateAndTime;
        this.hostName = hostName;
    }

    public int getEventId() { return eventId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDateAndTime() { return dateAndTime; }
    public String getHostName() { return hostName; }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}