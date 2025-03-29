package app.entities.team12;

import java.sql.Timestamp;

// Trying out pull requests comment to apply change
public class Team12SleepRecords {
    private int recordId;
    private int userId;
    private Timestamp sleepStart;
    private Timestamp sleepEnd;
    private double sleepDuration;
    private String createAt;

    public Team12SleepRecords(int recordId, int userId, Timestamp sleepStart, Timestamp sleepEnd, double sleepDuration, String createAt) {
        this.recordId = recordId;
        this.userId = userId;
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.sleepDuration = sleepDuration;
        this.createAt = createAt;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getSleepStart() {
        return sleepStart;
    }

    public void setSleepStart(Timestamp sleepStart) {
        this.sleepStart = sleepStart;
    }

    public Timestamp getSleepEnd() {
        return sleepEnd;
    }

    public void setSleepEnd(Timestamp sleepEnd) {
        this.sleepEnd = sleepEnd;
    }

    public double getSleepDuration() {
        return sleepDuration;
    }

    public void setSleepDuration(double sleepDuration) {
        this.sleepDuration = sleepDuration;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
