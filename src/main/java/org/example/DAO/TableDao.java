package org.example.DAO;

import org.example.Classes.Table;
import org.example.Constants.TableNames;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableDao {
    Connection connection;

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
                System.out.println("TABLE NOT SAVED");
            }

        }catch (SQLException e){
            System.out.println("TABLE SAVE SQL EXCEPTION");
        }

    }

    public void update(){}

    public void delete(){}
}
