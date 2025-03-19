package app.entities;

import java.math.BigInteger;

public class LifehackTeam08User {
    private int id;
    private String username;
    private String password;
    private BigInteger eggs;
    private int chickenFeed;
    private int predator;

    public LifehackTeam08User(int id, String username, String password, BigInteger eggs, int chickenFeed, int predator){
        this.id = id;
        this.username = username;
        this.password = password;
        this.eggs = eggs;
        this.chickenFeed = chickenFeed;
        this.predator = predator;

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

    public int getChickenFeed() {
        return chickenFeed;
    }

    public void setChickenFeed(int chickenFeed) {
        this.chickenFeed = chickenFeed;
    }

    public int getPredator() {
        return predator;
    }

    public void setPredator(int predator) {
        this.predator = predator;
    }
}
