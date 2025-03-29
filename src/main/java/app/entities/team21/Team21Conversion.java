package app.entities.team21;

public class Team21Conversion {

    private int id;
    private String units;
    private double value;
    private String date;

    public Team21Conversion(int id, String units, double value, String date) {
        this.id = id;
        this.units = units;
        this.value = value;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getUnits() {
        return units;
    }

    public double getValue() {
        return value;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "id=" + id +
                ", units='" + units + '\'' +
                ", value=" + value +
                ", date='" + date + '\'' +
                '}';
    }
}
