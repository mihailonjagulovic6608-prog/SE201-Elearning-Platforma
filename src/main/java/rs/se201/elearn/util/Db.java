package rs.se201.elearn.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    private static final String URL = "jdbc:sqlite:elearn.db";

    public static Connection open() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
