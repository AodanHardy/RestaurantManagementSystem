package org.example;

import org.example.DAO.DatabaseConnection;
import org.example.DAO.DatabaseTableBuilder;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();

        DatabaseTableBuilder tableBuilder = new DatabaseTableBuilder(connection);

        tableBuilder.createTables();
    }
}