package org.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/restaurant_management_system_db";
        String username = "aodan";
        String password = "amymagee";

        return DriverManager.getConnection(url, username, password);
    }
}
