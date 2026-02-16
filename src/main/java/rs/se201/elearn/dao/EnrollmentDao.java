package rs.se201.elearn.dao;

import rs.se201.elearn.util.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EnrollmentDao {

    public int enroll(int userId, int courseId) {
        String sql = "INSERT INTO enrollments(user_id, course_id, payment_status) VALUES(?,?,?)";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.setInt(2, courseId);
            ps.setString(3, "PAID");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) { rs.next(); return rs.getInt(1); }
        } catch (Exception e) {
            throw new RuntimeException("enroll error: " + e.getMessage(), e);
        }
    }
}
