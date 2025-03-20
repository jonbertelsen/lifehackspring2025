package app.entities;

public class Team7Jokes {
    private int categoryId;
    private String jokeNames;

    public Team7Jokes(int categoryId, String jokeNames) {
        this.categoryId = categoryId;
        this.jokeNames = jokeNames;
    }

    public Team7Jokes(String jokeNames) {
        this.jokeNames = jokeNames;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getJokeNames() {
        return jokeNames;
    }

    @Override
    public String toString() {
        return "Team7Jokes{" +
                "categoryId=" + categoryId +
                ", jokeNames='" + jokeNames + '\'' +
                '}';
    }
}
