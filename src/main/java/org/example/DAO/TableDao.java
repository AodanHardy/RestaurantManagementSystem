package org.example.DAO;

import org.example.Classes.Table;
import org.example.Constants.TableNames;
import static org.example.Constants.ColumnNames.Tables.*;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableDao {
    Logger logger = new Logger(TableDao.class);
    private Connection connection;

    public TableDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Table table) {
        String sql = String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                TableNames.TABLES,  TABLE_NUMBER, CAPACITY
        );

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, table.getTableNumber());
            statement.setInt(2, table.getCapacity());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                logger.error("TABLE NOT SAVED");
            }else {
                logger.info(String.format("TABLE %s SAVED", (table.getTableNumber())));
            }

        }catch (SQLException e){
            logger.error("TABLE SAVE SQL EXCEPTION: "+e.getMessage());
        }

    }

    public void update(){}

    public void delete(){}
}
