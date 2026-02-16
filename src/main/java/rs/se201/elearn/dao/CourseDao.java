package rs.se201.elearn.dao;

import rs.se201.elearn.model.Course;
import rs.se201.elearn.util.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {

    public int insert(String title, String category, String level, int instructorId) {
        String sql = "INSERT INTO courses(title, category, level, status, instructor_id) VALUES(?,?,?,?,?)";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, title);
            ps.setString(2, category);
            ps.setString(3, level);
            ps.setString(4, "DRAFT");
            ps.setInt(5, instructorId);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); return rs.getInt(1); }
        } catch (Exception e) {
            throw new RuntimeException("insert course error: " + e.getMessage(), e);
        }
    }

    public void publish(int courseId) {
        String sql = "UPDATE courses SET status='PUBLISHED' WHERE id = ?";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("publish error: " + e.getMessage(), e);
        }
    }

    public List<Course> listPublished() {
        String sql = "SELECT id, title, category, level, status, instructor_id FROM courses WHERE status='PUBLISHED'";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Course> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new Course(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("category"),
                        rs.getString("level"),
                        rs.getString("status"),
                        rs.getInt("instructor_id")
                ));
            }
            return out;
        } catch (Exception e) {
            throw new RuntimeException("listPublished error: " + e.getMessage(), e);
        }
    }

    // SQL injection primer (NE KORISTI) — ostavljeno radi dokumentacije:
    // String sql = "SELECT * FROM courses WHERE title LIKE '%" + term + "%'";
}
