package org.example;

import org.example.Config.Config;
import org.example.Config.ConfigMapper;
import org.example.Config.DatabaseConfig;
import org.example.DAO.DatabaseConnection;
import org.example.DAO.DatabaseTableBuilder;
import org.example.DAO.UserDao;
import org.example.Users.StaffType;
import org.example.Users.User;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Config config = ConfigMapper.getConfig();

        if (config == null){
            return;
        }


        Connection connection = DatabaseConnection.getConnection(config.getDatabaseConfig());

        // Running table builders to ensure database is built
        DatabaseTableBuilder tableBuilder = new DatabaseTableBuilder(connection);
        tableBuilder.createTables();






    }
}