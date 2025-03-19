package app.enums;

public enum ColorTeam4 {
    BLACK(1),
    WHITE(2),
    BROWN(3),
    MIXED(4),
    ORANGE(5),
    GREY(6);

    private final int code;

    ColorTeam4(int code) {
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public static ColorTeam4 enumFromCode(int code){
        for(ColorTeam4 color : values()){
            if(color.getCode() == code){
                return color;
            }
        }
        throw new IllegalArgumentException("ColorTeam4 code not found\nYou input needs to be between 1 and "+ ColorTeam4.values().length +  "\n" + code);
    }
}
