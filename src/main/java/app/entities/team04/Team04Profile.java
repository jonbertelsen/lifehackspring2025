package app.entities.team04;

import java.util.Objects;

public class Team04Profile {

    private int color;
    private int species;
    private String bio;
    private String name;
    private int age;
    private String image;
    private String email;
    private String password;
    private int id;

    public Team04Profile(int color, int species, String bio, String name, int age, String image, String email, String password, int id)
    {
        this.color = color;
        this.species = species;
        this.bio = bio;
        this.name = name;
        this.age = age;
        this.image = image;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public Team04Profile(int color, int species, String bio, String name, int age) {
        this.color = color;
        this.species = species;
        this.bio = bio;
        this.name = name;
        this.age = age;
    }

    public Team04Profile(int color, int species, String bio, String name, int age, String email) {
        this.color = color;
        this.species = species;
        this.bio = bio;
        this.name = name;
        this.age = age;
        this.email = email;
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

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public int getId() {
        return this.id;
    }


    @Override
    public String toString() {
        return "Color: " + this.color + " Species: " + this.species + " Bio: " + this.bio +
                " Name: " + this.name + " Age: " + this.age + " Image: " + this.image + " Email: " + this.email + " ID: " + this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Team04Profile that)) return false;
        return color == that.color && species == that.species && age == that.age && Objects.equals(bio, that.bio) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, species, bio, name, age);
    }
}
