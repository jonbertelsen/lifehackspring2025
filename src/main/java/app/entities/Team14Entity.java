package app.entities;

public class Team14Entity {
    private String title;
    private int duration;
    private String director;
    private String main_genre;

    public Team14Entity(String title, int duration, String director, String main_genre) {
        this.title = title;
        this.duration = duration;
        this.director = director;
        this.main_genre = main_genre;
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

    public String getMain_genre() {
        return main_genre;
    }

    @Override
    public String toString() {
        return "Team14Entity{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                ", director='" + director + '\'' +
                ", main_genre='" + main_genre + '\'' +
                '}';
    }
}
