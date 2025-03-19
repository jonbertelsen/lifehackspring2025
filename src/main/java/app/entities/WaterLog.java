package app.entities;

import java.time.LocalDate;

public class WaterLog {

    private int water_Log_id;
    private int water_goal;
    private int water_ml;
    private LocalDate date;


    public WaterLog(int water_Log_id, int water_goal, int water_ml, LocalDate date) {
        this.water_Log_id = water_Log_id;
        this.water_goal = water_goal;
        this.water_ml = water_ml;
        this.date = date;
    }

    public WaterLog(int water_goal, int water_ml, LocalDate date) {
        this.water_goal = water_goal;
        this.water_ml = water_ml;
        this.date = date;
    }

    public int getWater_Log_id() {
        return water_Log_id;
    }

    public void setWater_Log_id(int water_Log_id) {
        this.water_Log_id = water_Log_id;
    }

    public int getWater_goal() {
        return water_goal;
    }

    public void setWater_goal(int water_goal) {
        this.water_goal = water_goal;
    }

    public int getWater_ml() {
        return water_ml;
    }

    public void setWater_ml(int water_ml) {
        this.water_ml = water_ml;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
