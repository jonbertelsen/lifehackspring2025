package app.entities.team12;

import java.util.Date;

public class Team12SleepAnalytics {
    private int analyticsId;
    private int userId;
    private Date sleepStartDate;
    private double totalSleep;
    private String lastUpdated;

    public Team12SleepAnalytics(int analyticsId, int userId, Date sleepStartDate, double totalSleep, String lastUpdated) {
        this.analyticsId = analyticsId;
        this.userId = userId;
        this.sleepStartDate = sleepStartDate;
        this.totalSleep = totalSleep;
        this.lastUpdated = lastUpdated;
    }

    public int getAnalyticsId() {
        return analyticsId;
    }

    public void setAnalyticsId(int analyticsId) {
        this.analyticsId = analyticsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getSleepStartDate() {
        return sleepStartDate;
    }

    public void setSleepStartDate(Date sleepStartDate) {
        this.sleepStartDate = sleepStartDate;
    }

    public double getTotalSleep() {
        return totalSleep;
    }

    public void setTotalSleep(double totalSleep) {
        this.totalSleep = totalSleep;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
