package org.example.DAO;

import java.sql.Connection;

public class MenuItemDao {
    Connection connection;

    public MenuItemDao(Connection connection) {
        this.connection = connection;
    }

    public void save(MenuItemDao menuItemDao){
        String sql = "INSERT INTO menuItems (username, password, staffType) VALUES (?, ?, ?)";
    }

    public void update(MenuItemDao menuItemDao){}

    public void delete(MenuItemDao menuItemDao){}
}
