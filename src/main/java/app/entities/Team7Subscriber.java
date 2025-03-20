package app.entities;

public class Team7Subscriber {
    private int subscriberId;
    private String email;

    public Team7Subscriber(int subscriberId, String email) {
        this.subscriberId = subscriberId;
        this.email = email;
    }


    public Team7Subscriber(String email) {
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
        return "Team7Subscriber{" +
                "subscriberId=" + subscriberId +
                ", email='" + email + '\'' +
                '}';
    }
}
