package app.entities;

public class Team6Movie {
    private String title;
    private double rating;
    private int releaseYear;
    private String imageFilename;

    public Team6Movie(String title, double rating, int releaseYear, String imageFilename) {
        this.title = title;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.imageFilename = imageFilename;
    }

    public Team6Movie(String title, double rating, int releaseYear) {
        this.title = title;
        this.rating = rating;
        this.releaseYear = releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    @Override
    public String toString() {
        return title + " (" + releaseYear + "). " + rating + "☆" ;

    }
}
