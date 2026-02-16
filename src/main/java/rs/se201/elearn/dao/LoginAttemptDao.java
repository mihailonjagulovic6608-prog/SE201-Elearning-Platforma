package rs.se201.elearn.dao;

import rs.se201.elearn.util.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginAttemptDao {

    public void insertAttempt(int userId, boolean success) {
        String sql = "INSERT INTO login_attempts(user_id, success) VALUES(?,?)";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, success ? 1 : 0);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("insertAttempt error: " + e.getMessage(), e);
        }
    }

    public boolean locked(int userId) {
        String sql = "SELECT COUNT(*) AS cnt " +
                "FROM login_attempts " +
                "WHERE user_id=? AND success=0 " +
                "AND attempt_time >= datetime('now','-10 minutes')";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt("cnt") >= 5;
            }
        } catch (Exception e) {
            throw new RuntimeException("locked error: " + e.getMessage(), e);
        }
    }
}
