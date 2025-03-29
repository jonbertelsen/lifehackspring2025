package app.entities.team07;

public class Team07Jokes {
    private int Id;
    private String jokeNames;

    public Team07Jokes(int Id, String jokeNames) {
        this.Id = Id;
        this.jokeNames = jokeNames;
    }

    public Team07Jokes(String jokeNames) {
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
        return "Team07Jokes{" +
                "categoryId=" + Id +
                ", jokeNames='" + jokeNames + '\'' +
                '}';
    }
}
