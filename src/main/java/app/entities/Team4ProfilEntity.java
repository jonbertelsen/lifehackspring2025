package app.entities;

public class Team4ProfilEntity {

    private int color;
    private int species;
    private String bio;
    private String name;
    private int age;
    private String image;
    private String email;
    private String password;
    private int id;

    public Team4ProfilEntity(int color, int species, String bio, String name, int age, String image, String email, String password, int id)
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
}
