package app.entities;

import java.math.BigInteger;

public class LifehackTeam08User {
    private int id;
    private String username;
    private String password;
    private BigInteger eggs;

    public LifehackTeam08User(int id, String username, String password, BigInteger eggs){
        this.id = id;
        this.username = username;
        this.password = password;
        this.eggs = eggs;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getEggs() {
        return eggs;
    }

    public void setEggs(BigInteger eggs) {
        this.eggs = eggs;
    }

    @Override
    public String toString(){
        return "LifehackTeam08User{" + "id=" + id + ", username='" + username +  ", eggs=" + eggs + '}';
    }

}
