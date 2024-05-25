package org.example.DAO;

import org.example.Constants.ColumnNames;
import org.example.Constants.TableNames;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;

import static org.example.Constants.ColumnNames.MenuItems.PRICE;
import static org.example.Constants.ColumnNames.Users.PASSWORD;
import static org.example.Constants.ColumnNames.Users.USERNAME;

public class GeneralDao {
    Logger logger = new Logger(OrderDao.class);
    Connection connection;

    public GeneralDao(Connection connection) {
        this.connection = connection;
    }

    public void updateInt(String tableName, String column, String pkColumn,  int id, int newInt){
        String sql = String.format(
                "UPDATE %s " +
                        "SET %s = ? " +
                        "WHERE %s = ? ",
                tableName, column, pkColumn
        );

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, newInt);
            statement.setInt(2, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                logger.error("UPDATE INT NOT UPDATED");
            }else logger.info("UPDATE INT UPDATED");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void updateString(String tableName, String column, String pkColumn, int id, String newString){
        String sql = String.format(
                "UPDATE %s " +
                        "SET %s = ? " +
                        "WHERE %s = ? ",
                tableName, column, pkColumn
        );

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newString);
            statement.setInt(2, id);

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                logger.error("UPDATE STRING NOT UPDATED");
            }else logger.info("UPDATE STRING UPDATED");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    public void updateBool(String tableName, String column, int id, boolean newBool){

    }
    public void get(){

    }

}
