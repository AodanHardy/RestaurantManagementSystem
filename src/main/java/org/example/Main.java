package org.example;

import org.example.Classes.MenuItem;
import org.example.Classes.Reservation;
import org.example.Classes.Table;
import org.example.Config.Config;
import org.example.Config.ConfigMapper;
import org.example.DAO.*;
import org.example.Database.DatabaseConnection;
import org.example.Database.DatabaseTableBuilder;
import org.example.Logging.Logger;
import org.example.Orders.Order;
import org.example.Orders.OrderItem;
import org.example.Users.StaffType;
import org.example.Users.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;

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

//        Table table = new Table(1, 3);
//        TableDao tableDao = new TableDao(connection);
//        tableDao.save(table);
//
//        UserDao userDao = new UserDao(connection);
//        User user = new User("aodan", StaffType.MANAGER);
//        user.setPassword("test");
//        userDao.save(user);


        Order order = new Order(1, 1);

        order.addToOrderItems(new OrderItem(1, 3));

        order.addToOrderItems(new OrderItem(2, 2));

        OrderDao orderDao = new OrderDao(connection);

        orderDao.save(order);




        /**
         * Testing
         */



    }
}