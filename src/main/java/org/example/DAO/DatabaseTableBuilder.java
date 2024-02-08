package org.example.DAO;

import org.example.Users.StaffType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTableBuilder {
    private Connection connection;

    public DatabaseTableBuilder(Connection connection) {
        this.connection = connection;
    }

    public void createTables(){
        try (Statement statement = connection.createStatement()) {

            // Create Users Table
            StringBuilder createUserTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS users (");
            createUserTableSQL.append("user_id SERIAL PRIMARY KEY,");
            createUserTableSQL.append("username VARCHAR(255) UNIQUE NOT NULL,");
            createUserTableSQL.append("password VARCHAR(255) NOT NULL,");
            createUserTableSQL.append("staffType VARCHAR(255) NOT NULL");

            createUserTableSQL.append(")");

            statement.executeUpdate(createUserTableSQL.toString());





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
