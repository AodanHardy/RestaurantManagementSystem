package org.example.DAO;

import org.example.Classes.Reservation;
import org.example.Constants.TableNames;
import org.example.Logging.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.example.Constants.ColumnNames.Reservations.*;

public class ReservationDao {
    private final Logger logger = new Logger(ReservationDao.class);
    private final Connection connection;

    public ReservationDao(Connection connection) {
        this.connection = connection;
    }

    // GET all
    public List<Reservation> getAll() throws SQLException {
        String sql = String.format(
                "SELECT %s, %s, %s, %s, %s, %s FROM %s ORDER BY %s, %s, %s",
                RESERVATION_ID, TABLE_NUMBER, RESERVATION_NAME, DATE, START_TIME, END_TIME,
                TableNames.RESERVATIONS, DATE, TABLE_NUMBER, START_TIME
        );

        List<Reservation> out = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    // GET by id
    public Reservation getById(int id) throws SQLException {
        String sql = String.format(
                "SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE %s = ?",
                RESERVATION_ID, TABLE_NUMBER, RESERVATION_NAME, DATE, START_TIME, END_TIME,
                TableNames.RESERVATIONS, RESERVATION_ID
        );

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    // SEARCH (optional filters)
    public List<Reservation> search(Integer tableNumber, LocalDate fromDate, LocalDate toDate) throws SQLException {
        StringBuilder sql = new StringBuilder(String.format(
                "SELECT %s, %s, %s, %s, %s, %s FROM %s WHERE 1=1",
                RESERVATION_ID, TABLE_NUMBER, RESERVATION_NAME, DATE, START_TIME, END_TIME,
                TableNames.RESERVATIONS
        ));

        List<Object> params = new ArrayList<>();
        if (tableNumber != null) { sql.append(" AND ").append(TABLE_NUMBER).append(" = ?"); params.add(tableNumber); }
        if (fromDate != null)   { sql.append(" AND ").append(DATE).append(" >= ?");        params.add(Date.valueOf(fromDate)); }
        if (toDate != null)     { sql.append(" AND ").append(DATE).append(" <= ?");        params.add(Date.valueOf(toDate)); }
        sql.append(String.format(" ORDER BY %s, %s, %s", DATE, TABLE_NUMBER, START_TIME));

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                List<Reservation> out = new ArrayList<>();
                while (rs.next()) out.add(map(rs));
                return out;
            }
        }
    }

    // CREATE
    public Reservation save(Reservation r) throws SQLException {
        String sql = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                TableNames.RESERVATIONS, TABLE_NUMBER, RESERVATION_NAME, DATE, START_TIME, END_TIME
        );

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getTableNumber());
            ps.setString(2, r.getReservationName());
            ps.setDate(3, Date.valueOf(r.getDate()));
            ps.setTime(4, r.getStartTime());
            ps.setTime(5, r.getEndTime());

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Reservation not saved");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) r.setReservationId(keys.getInt(1));
            }
            logger.info("RESERVATION SAVED id=" + r.getReservationId());
        }
        return getById(r.getReservationId());
    }

    // UPDATE
    public Reservation update(Reservation r) throws SQLException {
        String sql = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                TableNames.RESERVATIONS,
                TABLE_NUMBER, RESERVATION_NAME, DATE, START_TIME, END_TIME,
                RESERVATION_ID
        );

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, r.getTableNumber());
            ps.setString(2, r.getReservationName());
            ps.setDate(3, Date.valueOf(r.getDate()));
            ps.setTime(4, r.getStartTime());
            ps.setTime(5, r.getEndTime());
            ps.setInt(6, r.getReservationId());

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Reservation not updated: " + r.getReservationId());

            logger.info("RESERVATION UPDATED id=" + r.getReservationId());
        }
        return getById(r.getReservationId());
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TableNames.RESERVATIONS, RESERVATION_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("Reservation not found or not deleted: " + id);
            logger.info("RESERVATION DELETED id=" + id);
        }
    }

    // OVERLAP CHECK (same table + date, time ranges intersect)
    public boolean overlaps(int tableNumber, LocalDate date, Time start, Time end, Integer excludeId) throws SQLException {
        StringBuilder sql = new StringBuilder(String.format(
                "SELECT 1 FROM %s WHERE %s = ? AND %s = ? AND NOT (%s <= ? OR %s >= ?)",
                TableNames.RESERVATIONS, TABLE_NUMBER, DATE, END_TIME, START_TIME
        ));
        List<Object> params = new ArrayList<>();
        params.add(tableNumber);
        params.add(Date.valueOf(date));
        params.add(start);
        params.add(end);

        if (excludeId != null) {
            sql.append(" AND ").append(RESERVATION_ID).append(" <> ?");
            params.add(excludeId);
        }

        try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
            setParams(ps, params);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static void setParams(PreparedStatement ps, List<Object> params) throws SQLException {
        int i = 1;
        for (Object p : params) {
            if (p instanceof Integer x) ps.setInt(i++, x);
            else if (p instanceof String s) ps.setString(i++, s);
            else if (p instanceof Date d) ps.setDate(i++, d);
            else if (p instanceof Time t) ps.setTime(i++, t);
            else throw new SQLException("Unsupported param type: " + p);
        }
    }

    private Reservation map(ResultSet rs) throws SQLException {
        Reservation r = new Reservation();
        r.setReservationId(rs.getInt(RESERVATION_ID));
        r.setTableNumber(rs.getInt(TABLE_NUMBER));
        r.setReservationName(rs.getString(RESERVATION_NAME));
        r.setDate(rs.getDate(DATE).toLocalDate());
        r.setStartTime(rs.getTime(START_TIME));
        r.setEndTime(rs.getTime(END_TIME));
        return r;
    }
}