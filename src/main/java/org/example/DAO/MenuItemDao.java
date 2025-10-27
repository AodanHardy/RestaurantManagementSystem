package org.example.DAO;

import org.example.Classes.MenuItem;
import static org.example.Constants.ColumnNames.MenuItems.*;

import org.example.Constants.TableNames;
import org.example.Logging.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDao {
    Logger logger = new Logger(MenuItemDao.class);
    private final Connection connection;

    public MenuItemDao(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public MenuItem save(MenuItem menuItem) throws SQLException {
        String sql = String.format(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
                TableNames.MENU_ITEMS, ITEM_NAME, DESCRIPTION, PRICE, ACTIVE
        );

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, menuItem.getItemName());
            statement.setString(2, menuItem.getDescription());
            statement.setDouble(3, menuItem.getPrice());
            statement.setBoolean(4, menuItem.isActive());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Menu item not saved");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    menuItem.setItemId(generatedKeys.getInt(1));
                    logger.info("MENU ITEM " + menuItem.getItemName() + " SAVED");
                }
            }
        }

        return menuItem;
    }

    // READ ALL
    public List<MenuItem> getAll() throws SQLException {
        List<MenuItem> items = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s ORDER BY %s", TableNames.MENU_ITEMS, ITEM_ID);

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                MenuItem item = new MenuItem();
                item.setItemId(rs.getInt(ITEM_ID));
                item.setItemName(rs.getString(ITEM_NAME));
                item.setDescription(rs.getString(DESCRIPTION));
                item.setPrice(rs.getDouble(PRICE));
                item.setActive(rs.getBoolean(ACTIVE));
                items.add(item);
            }
        }

        return items;
    }

    // READ ONE
    public MenuItem getById(int id) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TableNames.MENU_ITEMS, ITEM_ID);
        MenuItem item = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    item = new MenuItem();
                    item.setItemId(rs.getInt(ITEM_ID));
                    item.setItemName(rs.getString(ITEM_NAME));
                    item.setDescription(rs.getString(DESCRIPTION));
                    item.setPrice(rs.getDouble(PRICE));
                    item.setActive(rs.getBoolean(ACTIVE));
                }
            }
        }

        return item;
    }

    // UPDATE
    public MenuItem update(MenuItem menuItem) throws SQLException {
        String sql = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TableNames.MENU_ITEMS, ITEM_NAME, DESCRIPTION, PRICE, ACTIVE, ITEM_ID
        );

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, menuItem.getItemName());
            statement.setString(2, menuItem.getDescription());
            statement.setDouble(3, menuItem.getPrice());
            statement.setBoolean(4, menuItem.isActive());
            statement.setInt(5, menuItem.getItemId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Menu item not updated: " + menuItem.getItemId());
            }

            logger.info(String.format("MENU ITEM ID %s UPDATED", menuItem.getItemId()));
        }

        return getById(menuItem.getItemId());
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TableNames.MENU_ITEMS, ITEM_ID);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Menu item not found or not deleted: " + id);
            }

            logger.info(String.format("MENU ITEM ID %s DELETED", id));
        }
    }

    // EXTRA: get price by item ID
    public double getPrice(int itemId) throws SQLException {
        String sql = String.format("SELECT %s FROM %s WHERE %s = ?", PRICE, TableNames.MENU_ITEMS, ITEM_ID);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, itemId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble(PRICE);
                }
            }
        }
        throw new SQLException("Item ID not found: " + itemId);
    }
}