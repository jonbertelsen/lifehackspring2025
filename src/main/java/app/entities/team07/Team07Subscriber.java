package app.entities.team07;

public class Team07Subscriber {
    private int subscriberId;
    private String email;

    public Team07Subscriber(int subscriberId, String email) {
        this.subscriberId = subscriberId;
        this.email = email;
    }


    public Team07Subscriber(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Team07Subscriber{" +
                "subscriberId=" + subscriberId +
                ", email='" + email + '\'' +
                '}';
    }
}
