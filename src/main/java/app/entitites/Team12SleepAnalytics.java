package app.entitites;

import java.security.Timestamp;
import java.util.Date;

public class Team12SleepAnalytics {
    private int analyticsId;
    private int userId;
    private Date sleepStartDate;
    private double totalSleep;
    private Timestamp lastUpdated;

    public Team12SleepAnalytics(int analyticsId, int userId, Date sleepStartDate, double totalSleep, Timestamp lastUpdated) {
        this.analyticsId = analyticsId;
        this.userId = userId;
        this.sleepStartDate = sleepStartDate;
        this.totalSleep = totalSleep;
        this.lastUpdated = lastUpdated;
    }
}
