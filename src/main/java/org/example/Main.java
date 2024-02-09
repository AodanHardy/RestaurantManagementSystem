package org.example;

import org.example.Config.Config;
import org.example.Config.ConfigMapper;
import org.example.Database.DatabaseConnection;
import org.example.Database.DatabaseTableBuilder;


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