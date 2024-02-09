package org.example.Database;

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


            // Create Tables Table
            StringBuilder createTablesTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS tables (");
            createTablesTableSQL.append("table_id SERIAL PRIMARY KEY,");
            createTablesTableSQL.append("table_number int UNIQUE NOT NULL,");
            createTablesTableSQL.append("capacity int NOT NULL");
            createTablesTableSQL.append(")");
            statement.executeUpdate(createTablesTableSQL.toString());

            // Create Reservation Table
            StringBuilder createReservationTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS reservation (");
            createReservationTableSQL.append("reservation_id SERIAL PRIMARY KEY,");
            createReservationTableSQL.append("table_id INT NOT NULL,");
            createReservationTableSQL.append("date DATE NOT NULL,");
            createReservationTableSQL.append("start_time TIME NOT NULL,");
            createReservationTableSQL.append("end_time TIME NOT NULL,");
            createReservationTableSQL.append("FOREIGN KEY (table_id) REFERENCES tables(table_id)");
            createReservationTableSQL.append(")");

            statement.executeUpdate(createReservationTableSQL.toString());

            // Create MenuItems Table
            StringBuilder createMenuItemsTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS menuItems (");
            createMenuItemsTableSQL.append("itemId SERIAL PRIMARY KEY,");
            createMenuItemsTableSQL.append("itemName VARCHAR(255) NOT NULL,");
            createMenuItemsTableSQL.append("description VARCHAR(225),");
            createMenuItemsTableSQL.append("price DOUBLE PRECISION NOT NULL");
            createMenuItemsTableSQL.append(")");

            statement.executeUpdate(createMenuItemsTableSQL.toString());


            // Create Orders Table
            StringBuilder createOrdersTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS orders (");
            createOrdersTableSQL.append("orderId SERIAL PRIMARY KEY,");
            createOrdersTableSQL.append("tableId INT,");
            createOrdersTableSQL.append("userId INT,");
            createOrdersTableSQL.append("time TIMESTAMP NOT NULL,");
            createOrdersTableSQL.append("total DOUBLE PRECISION NOT NULL,");
            createOrdersTableSQL.append("isPaid BOOLEAN NOT NULL,");
            createOrdersTableSQL.append("FOREIGN KEY (tableId) REFERENCES tables(table_id),");
            createOrdersTableSQL.append("FOREIGN KEY (userId) REFERENCES users(user_id)");
            createOrdersTableSQL.append(")");

            statement.executeUpdate(createOrdersTableSQL.toString());


            // Create OrderItems Table
            StringBuilder createOrderItemsTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS order_items (");
            createOrderItemsTableSQL.append("orderItemId SERIAL PRIMARY KEY,");
            createOrderItemsTableSQL.append("orderId INT,");
            createOrderItemsTableSQL.append("itemId INT,");
            createOrderItemsTableSQL.append("quantity INT NOT NULL,");
            createOrderItemsTableSQL.append("subtotal DOUBLE PRECISION NOT NULL,");
            createOrderItemsTableSQL.append("FOREIGN KEY (orderId) REFERENCES orders(orderId),");
            createOrderItemsTableSQL.append("FOREIGN KEY (itemId) REFERENCES menuItems(itemId)");
            createOrderItemsTableSQL.append(")");

            statement.executeUpdate(createOrderItemsTableSQL.toString());



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
