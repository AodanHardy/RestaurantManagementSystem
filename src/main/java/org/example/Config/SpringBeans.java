package org.example.Config;

import org.example.DAO.*;
import org.example.Database.DatabaseConnection;
import org.example.Database.DatabaseTableBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class SpringBeans {

    @Bean
    public Connection connection() throws SQLException {
        Config cfg = ConfigMapper.getConfig();
        Connection conn = DatabaseConnection.getConnection(cfg.getDatabaseConfig());
        new DatabaseTableBuilder(conn).createTables();
        return conn;
    }

    @Bean public MenuItemDao menuItemDao(Connection c) { return new MenuItemDao(c); }
    @Bean public TableDao tableDao(Connection c) { return new TableDao(c); }
    @Bean public UserDao userDao(Connection c) { return new UserDao(c); }
    @Bean public OrderDao orderDao(Connection c) { return new OrderDao(c); }
    @Bean public ReservationDao reservationDao(Connection c) { return new ReservationDao(c); }
    @Bean public GeneralDao generalDao(Connection c) { return new GeneralDao(c); }
}