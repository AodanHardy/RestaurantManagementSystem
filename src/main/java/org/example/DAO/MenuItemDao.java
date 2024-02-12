package org.example.DAO;

import org.example.Classes.MenuItem;
import org.example.Constants.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemDao {
    Connection connection;

    public MenuItemDao(Connection connection) {
        this.connection = connection;
    }

    public void save(MenuItem menuItem){
        String sql = "INSERT INTO " + TableNames.MENU_ITEMS + " (item_name, description, price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, menuItem.getItemName());
            statement.setString(2, menuItem.getDescription());
            statement.setString(3, String.valueOf(menuItem.getPrice()));

            int affectedRows = statement.executeUpdate();

            // check if item was saved
            if (affectedRows == 0) {
                System.out.println("MENUITEM NOT SAVED");
            }

            // get next to set id to passed in object
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    menuItem.setItemId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e){

        }
    }

    public void update(MenuItemDao menuItemDao){}

    public void delete(MenuItemDao menuItemDao){}
}
