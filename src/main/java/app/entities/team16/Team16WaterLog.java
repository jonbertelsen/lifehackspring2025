package app.entities.team16;

import java.time.LocalDate;

public class Team16WaterLog {

    private int waterLogId;
    private int waterGoal;
    private int waterMl;
    private LocalDate date;


    public Team16WaterLog(int waterLogId, int waterGoal, int waterMl, LocalDate date) {
        this.waterLogId = waterLogId;
        this.waterGoal = waterGoal;
        this.waterMl = waterMl;
        this.date = date;
    }

    public Team16WaterLog(int waterGoal, int waterMl, LocalDate date) {
        this.waterGoal = waterGoal;
        this.waterMl = waterMl;
        this.date = date;
    }

    public int getWaterLogId() {
        return waterLogId;
    }

    public void setWaterLogId(int waterLogId) {
        this.waterLogId = waterLogId;
    }

    public int getWaterGoal() {
        return waterGoal;
    }

    public void setWaterGoal(int waterGoal) {
        this.waterGoal = waterGoal;
    }

    public int getWaterMl() {
        return waterMl;
    }

    public void setWaterMl(int waterMl) {
        this.waterMl = waterMl;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
