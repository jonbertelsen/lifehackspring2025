package app.entities;

public class Team7Category {
    private int categoryId;
    private String categoryNames;

    public Team7Category(int categoryId, String categoryNames) {
        this.categoryId = categoryId;
        this.categoryNames = categoryNames;
    }

    public Team7Category(String categoryNames) {
        this.categoryNames = categoryNames;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryNames() {
        return categoryNames;
    }

    @Override
    public String toString() {
        return "Team7Category{" +
                "categoryId=" + categoryId +
                ", categoryNames='" + categoryNames + '\'' +
                '}';
    }
}
