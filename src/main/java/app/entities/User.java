package app.entities;

public class User {

    private int user_id;
    private String userName;
    private String password;

    public User(int user_id, String userName, String password){
        this.user_id = user_id;
        this.userName = userName;
        this.password = password;
    }

    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
