package app.entities.Team05;

import java.util.Date;

public class Workout {
    private int id;
    private String email;
    private int type_id;
    private String type;
    private int duration;
    private Date date;
    private String extraNotes;

    public Workout(int id, int type_id, String type, int duration, Date date, String extraNotes) {
        this.id = id;
        this.type_id = type_id;
        this.type = type;
        this.duration = duration;
        this.date = date;
        this.extraNotes = extraNotes;
    }

    public Workout(int id, int type_id, int duration, Date date, String extraNotes) {
        this.id = id;
        this.type_id = type_id;
        this.duration = duration;
        this.date = date;
    }


    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getType_id() {
        return type_id;
    }

    public int getDuration() {
        return duration;
    }

    public Date getDate() {
        return date;
    }

    public String getExtraNotes() {
        return extraNotes;
    }


    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", type_id=" + type_id +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", date=" + date +
                ", extraNotes='" + extraNotes + '\'' +
                '}';
    }
}
