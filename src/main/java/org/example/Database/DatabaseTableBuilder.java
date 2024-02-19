package org.example.Database;

import org.example.Constants.TableNames;
import org.example.Logging.Logger;
import org.example.Users.StaffType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTableBuilder {
    Logger logger = new Logger(DatabaseTableBuilder.class);
    private Connection connection;

    public DatabaseTableBuilder(Connection connection) {
        this.connection = connection;
    }

    public void createTables(){
        try (Statement statement = connection.createStatement()) {

            // Create Users Table
            StringBuilder createUserTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+ TableNames.USERS + " (");
            createUserTableSQL.append("user_id SERIAL PRIMARY KEY,");
            createUserTableSQL.append("username VARCHAR(255) UNIQUE NOT NULL,");
            createUserTableSQL.append("password VARCHAR(255) NOT NULL,");
            createUserTableSQL.append("staff_type VARCHAR(255) NOT NULL");
            createUserTableSQL.append(")");
            statement.executeUpdate(createUserTableSQL.toString());


            // Create Tables Table
            StringBuilder createTablesTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TableNames.TABLES + " (");
            createTablesTableSQL.append("table_number INT PRIMARY KEY,");
            createTablesTableSQL.append("capacity INT NOT NULL");
            createTablesTableSQL.append(")");

            statement.executeUpdate(createTablesTableSQL.toString());


            // Create Reservation Table
            StringBuilder createReservationTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.RESERVATIONS+" (");
            createReservationTableSQL.append("reservation_id SERIAL PRIMARY KEY,");
            createReservationTableSQL.append("table_number INT NOT NULL,");
            createReservationTableSQL.append("date DATE NOT NULL,");
            createReservationTableSQL.append("start_time TIME NOT NULL,");
            createReservationTableSQL.append("end_time TIME NOT NULL,");
            createReservationTableSQL.append("FOREIGN KEY (table_number) REFERENCES "+TableNames.TABLES+"(table_number)");
            createReservationTableSQL.append(")");

            statement.executeUpdate(createReservationTableSQL.toString());

            // Create MenuItems Table
            StringBuilder createMenuItemsTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.MENU_ITEMS+" (");
            createMenuItemsTableSQL.append("item_id SERIAL PRIMARY KEY,");
            createMenuItemsTableSQL.append("item_name VARCHAR(255) NOT NULL,");
            createMenuItemsTableSQL.append("description VARCHAR(225),");
            createMenuItemsTableSQL.append("price DOUBLE PRECISION NOT NULL");
            createMenuItemsTableSQL.append(")");

            statement.executeUpdate(createMenuItemsTableSQL.toString());


            // Create Orders Table
            StringBuilder createOrdersTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.ORDERS+" (");
            createOrdersTableSQL.append("order_id SERIAL PRIMARY KEY,");
            createOrdersTableSQL.append("table_number INT,");
            createOrdersTableSQL.append("user_id INT,");
            createOrdersTableSQL.append("time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
            createOrdersTableSQL.append("total DOUBLE PRECISION NOT NULL,");
            createOrdersTableSQL.append("is_paid BOOLEAN NOT NULL,");
            createOrdersTableSQL.append("is_canceled BOOLEAN NOT NULL,");
            createOrdersTableSQL.append("FOREIGN KEY (table_number) REFERENCES "+TableNames.TABLES+"(table_number),");
            createOrdersTableSQL.append("FOREIGN KEY (user_id) REFERENCES "+TableNames.USERS+"(user_id)");
            createOrdersTableSQL.append(")");

            statement.executeUpdate(createOrdersTableSQL.toString());


            // Create OrderItems Table
            StringBuilder createOrderItemsTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.ORDER_ITEMS+" (");
            createOrderItemsTableSQL.append("order_item_id SERIAL PRIMARY KEY,");
            createOrderItemsTableSQL.append("order_id INT NOT NULL,");
            createOrderItemsTableSQL.append("item_id INT NOT NULL,");
            createOrderItemsTableSQL.append("special_instructions varchar(255),");
            createOrderItemsTableSQL.append("quantity INT NOT NULL,");
            createOrderItemsTableSQL.append("subtotal DOUBLE PRECISION NOT NULL,");
            createOrderItemsTableSQL.append("FOREIGN KEY (order_id) REFERENCES "+TableNames.ORDERS+"(order_id),");
            createOrderItemsTableSQL.append("FOREIGN KEY (item_id) REFERENCES "+TableNames.MENU_ITEMS+"(item_id)");
            createOrderItemsTableSQL.append(")");

            statement.executeUpdate(createOrderItemsTableSQL.toString());

            logger.info("DATABASE TABLES BUILT");


        } catch (SQLException e) {
            logger.error("BUILDING TABLES THREW EXCEPTION: " + e.getMessage());
        }
    }

}
