package app.entities.team05;

public class Team05User {
    private String email;
    private String name;
    private int password;

    public Team05User(String email, String name, int password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Team05User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password=" + password +
                '}';
    }
}
