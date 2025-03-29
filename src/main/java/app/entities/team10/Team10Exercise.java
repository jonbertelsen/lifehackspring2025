package app.entities.team10;

public class Team10Exercise {
    private int exerciseId;
    private String name;
    private String description;

    // ✅ Default constructor (needed for JSON deserialization)
    public Team10Exercise() {
    }

    public Team10Exercise(int exerciseId, String name, String description) {
        this.exerciseId = exerciseId;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public int getExerciseId() { return exerciseId; }
    public void setExerciseId(int exerciseId) { this.exerciseId = exerciseId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "Exercise: " + name + " - " + description;
    }
}

