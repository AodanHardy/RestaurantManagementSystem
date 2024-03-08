package org.example.DAO;

import org.example.Classes.MenuItem;
import static org.example.Constants.ColumnNames.MenuItems.*;

import org.example.Constants.TableNames;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MenuItemDao {
    Logger logger = new Logger(MenuItemDao.class);
    private final Connection connection;

    public MenuItemDao(Connection connection) {
        this.connection = connection;
    }

    public void save(MenuItem menuItem) {
        String sql = String.format("INSERT INTO " + TableNames.MENU_ITEMS +
                " (%s, %s, %s, %s) VALUES (?, ?, ?, ?)", ITEM_NAME, DESCRIPTION, PRICE, ACTIVE);

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, menuItem.getItemName());
            statement.setString(2, menuItem.getDescription());
            statement.setDouble(3, menuItem.getPrice());
            statement.setBoolean(4, menuItem.isActive());


            int affectedRows = statement.executeUpdate();

            // check if item was saved
            if (affectedRows == 0) {
                logger.error("MENUITEM NOT SAVED");
            }

            // get next to set id to passed in object
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    menuItem.setItemId(generatedKeys.getInt(1));
                    logger.info("MENU ITEM "+ menuItem.getItemName() + " SAVED");
                } else {
                    logger.error("Creating user failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            logger.error("SAVE THREW EXCEPTION: " + e.getMessage());
        }
    }

    public void update(MenuItem menuItem) {
        String sql = String.format(
                "UPDATE %s " +
                "SET %s = ?, " +
                "%s = ?, " +
                "%s = ? " +
                "%s = ? " +
                "WHERE %s = ? ",
                TableNames.MENU_ITEMS, ITEM_NAME, DESCRIPTION, PRICE, ACTIVE, ITEM_ID);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, menuItem.getItemName());
            statement.setString(2, menuItem.getDescription());
            statement.setDouble(3, menuItem.getPrice());
            statement.setBoolean(4, menuItem.isActive());
            statement.setInt(5, menuItem.getItemId());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) logger.info(String.format("MENU ITEM ID : %s UPDATED", menuItem.getItemId()));
            else logger.error(String.format("MENU ITEM ID : %s NOT UPDATED", menuItem.getItemId()));

        } catch (Exception e){
            logger.error("UPDATE THREW EXCEPTION: " + e.getMessage());
        }


    }

    public void delete(MenuItemDao menuItemDao) {
    }

    public double getPrice(int itemId) throws SQLException {
        double price = -1; // Default value if item not found or price is negative

        String sql = String.format("SELECT %s FROM %s WHERE item_id = ?", PRICE, TableNames.MENU_ITEMS);

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            // Set the item ID parameter in the prepared statement
            statement.setInt(1, itemId);

            // Execute the query
            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if result set has any rows
                if (resultSet.next()) {
                    // Retrieve the price from the result set
                    price = resultSet.getDouble("price");
                } else {
                    logger.error("Item with ID " + itemId + " not found.");
                }
                return price;
            } catch (SQLException e) {
                logger.error("getPrice threw exception: " + e.getMessage());
                return price;
            }
        }
    }
}
