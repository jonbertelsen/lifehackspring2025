package app.entities.team06;

public class Team06Movie {
    private String title;
    private double rating;
    private int releaseYear;
    private String imageFilename;

    public Team06Movie(String title, double rating, int releaseYear, String imageFilename) {
        this.title = title;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.imageFilename = imageFilename;
    }

    public Team06Movie(String title, double rating, int releaseYear) {
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
