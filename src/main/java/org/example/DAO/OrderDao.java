package org.example.DAO;

import java.sql.Connection;

public class OrderDao {
    Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void save(){}

    public void update(){}

    public void delete(){}
}
