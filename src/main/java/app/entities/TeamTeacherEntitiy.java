package app.entities;

public class TeamTeacherEntitiy {

    private String philosopher;
    private String quote;

    public TeamTeacherEntitiy(String philosopher, String quote) {
        this.philosopher = philosopher;
        this.quote = quote;
    }

    public String getPhilosopher() {
        return philosopher;
    }

    public String getQuote() {
        return quote;
    }

}
