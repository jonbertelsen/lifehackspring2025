package app.entities.team07;

public class Team07Category {
    private int categoryId;
    private String categoryNames;

    public Team07Category(int categoryId, String categoryNames) {
        this.categoryId = categoryId;
        this.categoryNames = categoryNames;
    }

    public Team07Category(String categoryNames) {
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
        return "Team07Category{" +
                "categoryId=" + categoryId +
                ", categoryNames='" + categoryNames + '\'' +
                '}';
    }
}
