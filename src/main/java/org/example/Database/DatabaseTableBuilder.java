package org.example.Database;

import org.example.Constants.TableNames;
import org.example.Constants.ColumnNames.*;

import org.example.Logging.Logger;

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
            createUserTableSQL.append(Users.USER_ID + " SERIAL PRIMARY KEY,");
            createUserTableSQL.append(Users.USERNAME + " VARCHAR(255) UNIQUE NOT NULL,");
            createUserTableSQL.append(Users.PASSWORD + " VARCHAR(255) NOT NULL,");
            createUserTableSQL.append(Users.STAFF_TYPE + " VARCHAR(255) NOT NULL");
            createUserTableSQL.append(")");
            statement.executeUpdate(createUserTableSQL.toString());


            // Create Tables Table
            StringBuilder createTablesTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS " + TableNames.TABLES + " (");
            createTablesTableSQL.append(Tables.TABLE_NUMBER + " INT PRIMARY KEY,");
            createTablesTableSQL.append(Tables.CAPACITY + " INT NOT NULL");
            createTablesTableSQL.append(")");

            statement.executeUpdate(createTablesTableSQL.toString());


            // Create Reservation Table
            StringBuilder createReservationTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.RESERVATIONS+" (");
            createReservationTableSQL.append(Reservations.RESERVATION_ID + " SERIAL PRIMARY KEY,");
            createReservationTableSQL.append(Reservations.TABLE_NUMBER + " INT NOT NULL,");
            createReservationTableSQL.append(Reservations.RESERVATION_NAME + " VARCHAR(255) NOT NULL,");
            createReservationTableSQL.append(Reservations.DATE + " DATE NOT NULL,");
            createReservationTableSQL.append(Reservations.START_TIME + " TIME NOT NULL,");
            createReservationTableSQL.append(Reservations.END_TIME + " TIME NOT NULL,");
            createReservationTableSQL.append("FOREIGN KEY ("+Reservations.TABLE_NUMBER+") REFERENCES "
                    +TableNames.TABLES+"("+Tables.TABLE_NUMBER+")");
            createReservationTableSQL.append(")");

            statement.executeUpdate(createReservationTableSQL.toString());

            // Create MenuItems Table
            StringBuilder createMenuItemsTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.MENU_ITEMS+" (");
            createMenuItemsTableSQL.append(MenuItems.ITEM_ID + " SERIAL PRIMARY KEY,");
            createMenuItemsTableSQL.append(MenuItems.ITEM_NAME + " VARCHAR(255) NOT NULL,");
            createMenuItemsTableSQL.append(MenuItems.DESCRIPTION + " VARCHAR(225),");
            createMenuItemsTableSQL.append(MenuItems.PRICE + " DOUBLE PRECISION NOT NULL");
            createMenuItemsTableSQL.append(")");

            statement.executeUpdate(createMenuItemsTableSQL.toString());


            // Create Orders Table
            StringBuilder createOrdersTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.ORDERS+" (");
            createOrdersTableSQL.append(Orders.ORDER_ID + " SERIAL PRIMARY KEY,");
            createOrdersTableSQL.append(Orders.TABLE_NUMBER + " INT,");
            createOrdersTableSQL.append(Orders.USER_ID + " INT,");
            createOrdersTableSQL.append(Orders.TIME + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP,");
            createOrdersTableSQL.append(Orders.TOTAL + " DOUBLE PRECISION NOT NULL,");
            createOrdersTableSQL.append(Orders.IS_PAID + " BOOLEAN NOT NULL,");
            createOrdersTableSQL.append(Orders.IS_CANCELED + " BOOLEAN NOT NULL,");
            createOrdersTableSQL.append("FOREIGN KEY ("+Orders.TABLE_NUMBER+") REFERENCES "+TableNames.TABLES+"("+Tables.TABLE_NUMBER+"),");
            createOrdersTableSQL.append("FOREIGN KEY ("+ Orders.USER_ID +") REFERENCES "+TableNames.USERS+"("+Users.USER_ID+")");
            createOrdersTableSQL.append(")");

            statement.executeUpdate(createOrdersTableSQL.toString());


            // Create OrderItems Table
            StringBuilder createOrderItemsTableSQL = new StringBuilder("CREATE TABLE IF NOT EXISTS "+TableNames.ORDER_ITEMS+" (");
            createOrderItemsTableSQL.append(OrderItems.ORDER_ITEM_ID + " SERIAL PRIMARY KEY,");
            createOrderItemsTableSQL.append(OrderItems.ORDER_ID + " INT NOT NULL,");
            createOrderItemsTableSQL.append(OrderItems.ITEM_ID + " INT NOT NULL,");
            createOrderItemsTableSQL.append(OrderItems.SPECIAL_INSTRUCTIONS + " varchar(255),");
            createOrderItemsTableSQL.append(OrderItems.QUANTITY + " INT NOT NULL,");
            createOrderItemsTableSQL.append(OrderItems.SUBTOTAL + " DOUBLE PRECISION NOT NULL,");
            createOrderItemsTableSQL.append("FOREIGN KEY ("+OrderItems.ORDER_ID+") REFERENCES "+TableNames.ORDERS+"("+Orders.ORDER_ID+"),");
            createOrderItemsTableSQL.append("FOREIGN KEY ("+OrderItems.ITEM_ID+") REFERENCES "+TableNames.MENU_ITEMS+"("+MenuItems.ITEM_ID+")");
            createOrderItemsTableSQL.append(")");

            statement.executeUpdate(createOrderItemsTableSQL.toString());

            logger.info("DATABASE TABLES BUILT");


        } catch (SQLException e) {
            logger.error("BUILDING TABLES THREW EXCEPTION: " + e.getMessage());
        }
    }

}
