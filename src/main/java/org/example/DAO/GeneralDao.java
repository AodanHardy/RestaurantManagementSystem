package org.example.DAO;

import org.example.Logging.Logger;

import java.sql.Connection;

public class GeneralDao {
    Logger logger = new Logger(OrderDao.class);
    Connection connection;

    public GeneralDao(Connection connection) {
        this.connection = connection;
    }



}
