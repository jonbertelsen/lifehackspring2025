package app.entities.team17;

public class Team17Soda {

    private String name;
    private float liter;

    public Team17Soda(String name, float liter) {
        this.name = name;
        this.liter = liter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLiter() {
        return liter;
    }

    public void setLiter(float liter) {
        this.liter = liter;
    }

    @Override
    public String toString() {
        return "Team17Soda{" +
                "name='" + name + '\'' +
                ", liter=" + liter +
                '}';
    }
}


