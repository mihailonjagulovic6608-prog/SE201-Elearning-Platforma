package rs.se201.elearn.dao;

import rs.se201.elearn.model.User;
import rs.se201.elearn.util.Db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    public User findByEmail(String email) {
        String sql = "SELECT id, email, role, disabled FROM users WHERE email = ?";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getInt("disabled") == 1
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("findByEmail error: " + e.getMessage(), e);
        }
    }

    public String passwordHashByEmail(String email) {
        String sql = "SELECT password_hash FROM users WHERE email = ?";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return rs.getString("password_hash");
            }
        } catch (Exception e) {
            throw new RuntimeException("passwordHashByEmail error: " + e.getMessage(), e);
        }
    }

    public int insert(String email, String passwordHash, String role) {
        String sql = "INSERT INTO users(email, password_hash, role) VALUES(?,?,?)";
        try (var conn = Db.open(); PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, email);
            ps.setString(2, passwordHash);
            ps.setString(3, role);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException("insert user error: " + e.getMessage(), e);
        }
    }
}
