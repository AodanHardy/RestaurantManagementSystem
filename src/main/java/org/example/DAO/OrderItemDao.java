package org.example.DAO;

import java.sql.Connection;

public class OrderItemDao {
    Connection connection;

    public OrderItemDao(Connection connection) {
        this.connection = connection;
    }

    public void save(){}

    public void update(){}

    public void delete(){}
}
