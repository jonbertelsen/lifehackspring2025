package app.entities.Team05;

import java.util.Date;

public class WorkoutLog {
    private int id;
    private String email;
    private int typeId;
    private int duration;
    private Date date;

    public WorkoutLog(int id, String email, int typeId, int duration, Date date) {
        this.id = id;
        this.email = email;
        this.typeId = typeId;
        this.duration = duration;
        this.date = date;
    }

    public WorkoutLog(String email, int typeId, int duration, Date date) {
        this.id = id;
        this.email = email;
        this.typeId = typeId;
        this.duration = duration;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getTypeId() {
        return typeId;
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
                ", typeId=" + typeId +
                ", duration=" + duration +
                ", date=" + date +
                '}';
    }
}
