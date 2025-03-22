package app.entities.team10;

public class Team10TrainingSessionExercise {
        private int sessionId;
        private int exerciseId;

    public Team10TrainingSessionExercise(int sessionId, int exerciseId) {
        this.sessionId = sessionId;
        this.exerciseId = exerciseId;
    }

    // Getters and Setters
        public int getSessionId() { return sessionId; }
        public void setSessionId(int sessionId) { this.sessionId = sessionId; }

        public int getExerciseId() { return exerciseId; }
        public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

        @Override
        public String toString() {
            return "Training Session ID: " + sessionId + ", Exercise ID: " + exerciseId;
        }
}
