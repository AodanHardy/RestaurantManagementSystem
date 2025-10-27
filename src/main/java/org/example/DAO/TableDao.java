package org.example.DAO;

import org.example.Classes.Table;
import org.example.Constants.TableNames;

import static org.example.Constants.ColumnNames.Tables.*;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDao {
    Logger logger = new Logger(TableDao.class);
    private Connection connection;

    public TableDao(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public Table save(Table table) throws SQLException {
        final String sql = String.format(
                "INSERT INTO %s (%s, %s) VALUES (?, ?)",
                TableNames.TABLES, TABLE_NUMBER, CAPACITY
        );

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, table.getTableNumber());
            ps.setInt(2, table.getCapacity());
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("TABLE NOT SAVED");
            }
            logger.info(String.format("TABLE %s SAVED", table.getTableNumber()));
            // Return the canonical row from DB (in case of defaults)
            return getById(table.getTableNumber());
        }
    }

    // UPDATE
    public Table update(Table table) throws SQLException {
        final String sql = String.format(
                "UPDATE %s SET %s = ? WHERE %s = ?",
                TableNames.TABLES, CAPACITY, TABLE_NUMBER
        );

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, table.getCapacity());
            ps.setInt(2, table.getTableNumber());
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("TABLE NOT UPDATED (not found?): " + table.getTableNumber());
            }
            logger.info(String.format("TABLE NUM : %s UPDATED", table.getTableNumber()));
            return getById(table.getTableNumber());
        }
    }

    // READ ALL
    public List<Table> getAll() throws SQLException {
        final String sql = String.format(
                "SELECT %s, %s FROM %s ORDER BY %s",
                TABLE_NUMBER, CAPACITY, TableNames.TABLES, TABLE_NUMBER
        );

        List<Table> tables = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Table t = new Table();
                t.setTableNumber(rs.getInt(TABLE_NUMBER));
                t.setCapacity(rs.getInt(CAPACITY));
                tables.add(t);
            }
        }
        return tables;
    }

    // READ ONE
    public Table getById(int tableNumber) throws SQLException {
        final String sql = String.format(
                "SELECT %s, %s FROM %s WHERE %s = ?",
                TABLE_NUMBER, CAPACITY, TableNames.TABLES, TABLE_NUMBER
        );

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, tableNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null; // controller handles null -> 404/RuntimeException
                }
                Table t = new Table();
                t.setTableNumber(rs.getInt(TABLE_NUMBER));
                t.setCapacity(rs.getInt(CAPACITY));
                return t;
            }
        }
    }

    // DELETE
    public void delete(int tableNumber) throws SQLException {
        final String sql = String.format(
                "DELETE FROM %s WHERE %s = ?",
                TableNames.TABLES, TABLE_NUMBER
        );

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, tableNumber);
            int affected = ps.executeUpdate();
            if (affected == 0) {
                throw new SQLException("TABLE NOT FOUND OR NOT DELETED: " + tableNumber);
            }
            logger.info(String.format("TABLE NUM %s DELETED", tableNumber));
        }
    }
}
