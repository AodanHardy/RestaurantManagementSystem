package org.example;

import org.example.Classes.MenuItem;
import org.example.Classes.Reservation;
import org.example.Config.Config;
import org.example.Config.ConfigMapper;
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

        if (config == null){
            return;
        }
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



        /**
         * Testing
         */



    }
}