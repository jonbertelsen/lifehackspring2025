package app.entitites;

import java.time.LocalDateTime;

public class Team12SleepRecords {
    private int recordId;
    private int userId;
    private LocalDateTime sleepDate;
    private LocalDateTime sleepStart;
    private LocalDateTime sleepEnd;
    private double totalSleep;
    private LocalDateTime createAt;

    public Team12SleepRecords(int recordId, int userId, LocalDateTime sleepDate, LocalDateTime sleepStart, LocalDateTime sleepEnd, double totalSleep, LocalDateTime createAt) {
        this.recordId = recordId;
        this.userId = userId;
        this.sleepDate = sleepDate;
        this.sleepStart = sleepStart;
        this.sleepEnd = sleepEnd;
        this.totalSleep = totalSleep;
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

    public LocalDateTime getSleepDate() {
        return sleepDate;
    }

    public void setSleepDate(LocalDateTime sleepDate) {
        this.sleepDate = sleepDate;
    }

    public LocalDateTime getSleepStart() {
        return sleepStart;
    }

    public void setSleepStart(LocalDateTime sleepStart) {
        this.sleepStart = sleepStart;
    }

    public LocalDateTime getSleepEnd() {
        return sleepEnd;
    }

    public void setSleepEnd(LocalDateTime sleepEnd) {
        this.sleepEnd = sleepEnd;
    }

    public double getTotalSleep() {
        return totalSleep;
    }

    public void setTotalSleep(double totalSleep) {
        this.totalSleep = totalSleep;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "Team12SleepRecords{" +
                "recordId=" + recordId +
                ", userId=" + userId +
                ", sleepDate=" + sleepDate +
                ", sleepStart=" + sleepStart +
                ", sleepEnd=" + sleepEnd +
                ", totalSleep=" + totalSleep +
                ", createAt=" + createAt +
                '}';
    }
}
