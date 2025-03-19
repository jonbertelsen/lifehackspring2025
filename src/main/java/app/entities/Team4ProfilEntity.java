package app.entities;

public class Team4ProfilEntity {

    private int color;
    private int species;
    private String bio;
    private String name;
    private int age;
    private String image;


    public Team4ProfilEntity(int color, int species, String bio, String name, int age, String image) {
        this.color = color;
        this.species = species;
        this.bio = bio;
        this.name = name;
        this.age = age;
        this.image = image;
    }


    //Getter methods
    public int getColor() {
        return this.color;
    }

    public int getSpecies() {
        return this.species;
    }

    public String getBio() {
        return this.bio;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public String getImage() {
        return this.image;
    }


    @Override
    public String toString() {
        return "Color: " + this.color + " Species: " + this.species + " Bio: " + this.bio +
                " Name: " + this.name + " Age: " + this.age + " Image: " + this.image;
    }



}
