package app.entities;

import org.w3c.dom.Text;

public class LifehackTeam08Upgrades {
    private int id;
    private String name;
    private int tier;
    private int cost;

    public LifehackTeam08Upgrades(int id, String name, int tier, int cost){
        this.id = id;
        this.name = name;
        this.tier = tier;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    @Override
    public String toString(){
        return "LifehackTeam08Upgrades{" + "id=" + id + ", name=" + name + ", tier=" + tier + ", cost=" + cost + '}';
    }
}
