package app.entities;

public class Soda {

    private String name;
    private float liter;

    public Soda(String name, float liter) {
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
        return "Soda{" +
                "name='" + name + '\'' +
                ", liter=" + liter +
                '}';
    }
}


