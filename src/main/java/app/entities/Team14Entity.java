package app.entities;

public class Team14Entity {
    private String title = "Blinkende lygter";
    private int duration = 123;
    private String director;
    private String platform;

    public Team14Entity(String title, int duration, String director, String platform) {
        this.title = title;
        this.duration = duration;
        this.director = director;
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getDirector() {
        return director;
    }

    public String getPlatform() {
        return platform;
    }
}
