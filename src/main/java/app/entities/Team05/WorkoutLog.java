package app.entities.Team05;

import java.util.Date;

public class WorkoutLog {
    private int id;
    private String email;
    private int type_id;
    private int duration;
    private Date date;

    public WorkoutLog(int id, String email, int type_id, int duration, Date date) {
        this.id = id;
        this.email = email;
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

    @Override
    public String toString() {
        return "WorkoutLog{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", type_id=" + type_id +
                ", duration=" + duration +
                ", date=" + date +
                '}';
    }
}
