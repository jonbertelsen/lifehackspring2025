package app.enums;

public enum SpeciesTeam4 {
    CAT(1),
    DOG(2),
    RABBIT(3),
    HAMSTER(4),
    ASIANELEPHANT(5);

    private final int code;

    SpeciesTeam4(int code) {
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public static SpeciesTeam4 enumFromCode(int code){
        for(SpeciesTeam4 species : values()){
            if(species.getCode() == code){
                return species;
            }
        }
        throw new IllegalArgumentException("SpeciesTeam4 code not found\nYour input needs to be between 1 and " + SpeciesTeam4.values().length + "\n" + code);
    }
}

/*
SpeciesTeam4.values() returns an array of SpeciesTeam4 values.
SpeciesTeam4.valueOf(String name) returns the SpeciesTeam4 value whose name is equal to the given string.
SpeciesTeam4.values().get(int) returns the SpeciesTeam4 value at the given index.
 */