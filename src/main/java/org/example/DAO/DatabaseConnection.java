package org.example.DAO;

import org.example.Config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection getConnection(DatabaseConfig config) throws SQLException {
        String url = config.getUrl();
        String username = config.getUsername();
        String password = config.getPassword();

        return DriverManager.getConnection(url, username, password);
    }
}
