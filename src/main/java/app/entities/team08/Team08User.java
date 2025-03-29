package app.entities.team08;

import java.math.BigInteger;

public class Team08User {
    private int id;
    private String username;
    private String password;
    private BigInteger eggs;
    private int chickenFeedTier;
    private int predatorTier;

    public Team08User(int id, String username, String password, BigInteger eggs, int chickenFeedtier, int predatorTier){
        this.id = id;
        this.username = username;
        this.password = password;
        this.eggs = eggs;
        this.chickenFeedTier = chickenFeedtier;
        this.predatorTier = predatorTier;

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

    public int getChickenFeedTier() { return chickenFeedTier; }

    public int getPredatorTier() { return predatorTier; }

    public void setChickenFeedTier(int chickenFeedtier) {
        this.chickenFeedTier = chickenFeedtier;
    }

    public void setPredatorTier(int predatorTier) {
        this.predatorTier = predatorTier;
    }

    @Override

    public String toString(){
        return "Team08User{" + "id=" + id + ", username='" + username +  ", eggs=" + eggs + '}';
    }

}
