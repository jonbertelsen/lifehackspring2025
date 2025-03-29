package app.entities.team10;

import java.util.Date;
import java.util.List;

public class Team10TrainingSession {
    private int sessionId;
    private int userId;  // Reference to Team05User
    private Date sessionDate;
    private List<Integer> exerciseIds;  // List of Exercise IDs
    private int userSessionId;  // Team05User performing the session

    public Team10TrainingSession(int sessionId, int userId, Date sessionDate, List<Integer> exerciseIds, int userSessionId) {
        this.sessionId = sessionId;
        this.userId = userId;
        this.sessionDate = sessionDate;
        this.exerciseIds = exerciseIds;
        this.userSessionId = userSessionId;
    }

    // Getters and Setters
    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Date getSessionDate() { return sessionDate; }
    public void setSessionDate(Date sessionDate) { this.sessionDate = sessionDate; }

    public List<Integer> getExerciseIds() { return exerciseIds; }
    public void setExerciseIds(List<Integer> exerciseIds) { this.exerciseIds = exerciseIds; }

    public int getUserSessionId() { return userSessionId; }
    public void setUserSessionId(int userSessionId) { this.userSessionId = userSessionId; }

    @Override
    public String toString() {
        return "Training Session ID: " + sessionId + ", Team05User ID: " + userId + ", Date: " + sessionDate;
    }
}
