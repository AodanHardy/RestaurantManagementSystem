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
            createUserTableSQL.append("user_id INT PRIMARY KEY AUTO_INCREMENT,");
            createUserTableSQL.append("username VARCHAR(255) UNIQUE NOT NULL,");
            createUserTableSQL.append("password VARCHAR(255) NOT NULL,");
            createUserTableSQL.append("staffType ENUM(");

            // Add enum values dynamically
            StaffType[] staffTypes = StaffType.values();
            for (int i = 0; i < staffTypes.length; i++) {
                createUserTableSQL.append("'");
                createUserTableSQL.append(staffTypes[i].name());
                createUserTableSQL.append("'");
                if (i < staffTypes.length - 1) {
                    createUserTableSQL.append(",");
                }
            }

            createUserTableSQL.append(") NOT NULL");

            createUserTableSQL.append(")");

            statement.executeUpdate(createUserTableSQL.toString());





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
