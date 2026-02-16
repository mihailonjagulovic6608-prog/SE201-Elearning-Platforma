package rs.se201.elearn;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class H2SchemaTest {
    @Test
    void h2SchemaCreatesTables() throws Exception {
        try (Connection c = DriverManager.getConnection("jdbc:h2:mem:elearn_test;MODE=MySQL;DB_CLOSE_DELAY=-1", "sa", "");
             Statement st = c.createStatement()) {

            st.execute("CREATE TABLE users(id INT AUTO_INCREMENT PRIMARY KEY, email VARCHAR(190) UNIQUE, password_hash VARCHAR(255), role VARCHAR(30), disabled TINYINT DEFAULT 0, created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            st.execute("CREATE TABLE courses(id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), category VARCHAR(100), level VARCHAR(50), status VARCHAR(30), instructor_id INT)");
            st.execute("CREATE TABLE enrollments(id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, course_id INT, payment_status VARCHAR(30), enrolled_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");
            st.execute("CREATE TABLE login_attempts(id INT AUTO_INCREMENT PRIMARY KEY, user_id INT, success TINYINT, attempted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            assertDoesNotThrow(() -> st.executeQuery("SELECT * FROM users"));
        }
    }
}
