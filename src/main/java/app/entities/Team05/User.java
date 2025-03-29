package app.entities.Team05;

public class User {
    private String email;
    private String name;
    private int password;

    public User(String email, String name, int password) {
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
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password=" + password +
                '}';
    }
}
