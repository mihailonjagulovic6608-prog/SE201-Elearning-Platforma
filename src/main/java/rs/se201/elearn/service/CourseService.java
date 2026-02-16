package rs.se201.elearn.service;

import rs.se201.elearn.dao.CourseDao;
import rs.se201.elearn.model.Course;
import rs.se201.elearn.util.Validation;

import java.util.List;

public class CourseService {
    private final CourseDao dao = new CourseDao();

    public int createCourse(String title, String category, String level, int instructorId) {
        Validation.requireNonBlank(title, "title");
        Validation.requireNonBlank(category, "category");
        Validation.requireNonBlank(level, "level");
        if (instructorId <= 0) throw new IllegalArgumentException("instructorId mora biti > 0");
        return dao.insert(title, category, level, instructorId);
    }

    public void publishCourse(int courseId) {
        dao.publish(courseId);
    }

    public List<Course> listPublished() {
        return dao.listPublished();
    }
}
