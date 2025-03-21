package app.entities;

public class Team7Jokes {
    private int Id;
    private String jokeNames;

    public Team7Jokes(int Id, String jokeNames) {
        this.Id = Id;
        this.jokeNames = jokeNames;
    }

    public Team7Jokes(String jokeNames) {
        this.jokeNames = jokeNames;
    }

    public int getId() {
        return Id;
    }

    public String getJokeNames() {
        return jokeNames;
    }

    @Override
    public String toString() {
        return "Team7Jokes{" +
                "categoryId=" + Id +
                ", jokeNames='" + jokeNames + '\'' +
                '}';
    }
}
