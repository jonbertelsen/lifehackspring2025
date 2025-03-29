package app.entities;


// Klassen er kaldt "Team2Entity, men burde reelt set være kaldt "Excuse"
public class Team2Entity {
    int id;
    String excusetext;
    int sillyness_level;
    int category;

    public Team2Entity(int id, String excusetext, int sillyness_level, int category) {
        this.id = id;
        this.excusetext = excusetext;
        this.sillyness_level = sillyness_level;
        this.category = category;
    }

    public Team2Entity(int id, String excusetext, int category) {
        this.id = id;
        this.excusetext = excusetext;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSillyness_level() {
        return sillyness_level;
    }

    public void setSillyness_level(int sillyness_level) {
        this.sillyness_level = sillyness_level;
    }

    public String getExcusetext() {
        return excusetext;
    }

    public void setExcusetext(String excusetext) {
        this.excusetext = excusetext;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }
}
