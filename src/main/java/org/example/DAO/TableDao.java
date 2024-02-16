package org.example.DAO;

import org.example.Classes.Table;
import org.example.Constants.TableNames;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDao {
    Logger logger = new Logger(TableDao.class);
    private Connection connection;

    public TableDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Table table) {
        String sql = "INSERT INTO " + TableNames.TABLES + " (table_number, capacity) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, table.getTableNumber());
            statement.setInt(2, table.getCapacity());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                logger.error("TABLE NOT SAVED");
            }

        }catch (SQLException e){
            logger.error("TABLE SAVE SQL EXCEPTION: "+e.getMessage());
        }

    }

    public void update(){}

    public void delete(){}
}
