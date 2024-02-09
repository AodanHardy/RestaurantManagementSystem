package org.example.DAO;

import java.sql.Connection;

public class TableDao {
    Connection connection;

    public TableDao(Connection connection) {
        this.connection = connection;
    }

    public void save(){}

    public void update(){}

    public void delete(){}
}
