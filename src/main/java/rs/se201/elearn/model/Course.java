package rs.se201.elearn.model;

public class Course {
    public int id;
    public String title;
    public String category;
    public String level;
    public String status;
    public int instructorId;

    public Course(int id, String title, String category, String level, String status, int instructorId) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.level = level;
        this.status = status;
        this.instructorId = instructorId;
    }
}
