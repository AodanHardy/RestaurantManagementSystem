package org.example;

import org.example.Classes.MenuItem;
import org.example.Classes.Reservation;
import org.example.Config.Config;
import org.example.Config.ConfigMapper;
import org.example.Constants.ColumnNames;
import org.example.Constants.TableNames;
import org.example.DAO.*;
import org.example.Database.DatabaseConnection;
import org.example.Database.DatabaseTableBuilder;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) throws SQLException {
        Logger logger = new Logger(Main.class);

        // Load config
        Config config = ConfigMapper.getConfig();

        // set connection to db
        Connection connection = DatabaseConnection.getConnection(config.getDatabaseConfig());

        // Running table builders to ensure database is built
        DatabaseTableBuilder tableBuilder = new DatabaseTableBuilder(connection);
        tableBuilder.createTables();


        /**
         * Testing
         */
        TableDao tableDao = new TableDao(connection);
        UserDao userDao = new UserDao(connection);
        OrderDao orderDao = new OrderDao(connection);
        MenuItemDao menuItemDao = new MenuItemDao(connection);
        ReservationDao reservationDao = new ReservationDao(connection);
        GeneralDao generalDao = new GeneralDao(connection);


        generalDao.updateString(TableNames.MENU_ITEMS, ColumnNames.MenuItems.ITEM_NAME, ColumnNames.MenuItems.ITEM_ID, 1, "Burger");

        /**
         * Testing
         */



    }
}