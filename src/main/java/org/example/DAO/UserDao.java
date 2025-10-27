package org.example.DAO;

import org.example.Constants.TableNames;
import static org.example.Constants.ColumnNames.Users.*;
import org.example.Logging.Logger;
import org.example.Users.StaffType;
import org.example.Users.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final Logger logger = new Logger(UserDao.class);
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public User save(User user) throws SQLException {
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                TableNames.USERS, USERNAME, PASSWORD, STAFF_TYPE
        );

        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getHashedPassword());
            ps.setString(3, user.getRole().toString());

            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("USER NOT SAVED");

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) user.setUserId(keys.getInt(1));
                else throw new SQLException("USER NOT SAVED, NO ID OBTAINED");
            }
            logger.info(String.format("USER ID: %s, USERNAME: %s CREATED", user.getUserId(), user.getUsername()));
        }
        return getById(user.getUserId());
    }

    // READ (by id)
    public User getById(int id) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TableNames.USERS, USER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    // READ (by username)
    public User get(String username) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TableNames.USERS, USERNAME);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return map(rs);
            }
        }
        return null;
    }

    // READ all
    public List<User> getAll() throws SQLException {
        List<User> out = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s ORDER BY %s", TableNames.USERS, USER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) out.add(map(rs));
        }
        return out;
    }

    // UPDATE password (by id)
    public void updatePassword(int userId, String newPasswordHashed) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TableNames.USERS, PASSWORD, USER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newPasswordHashed);
            ps.setInt(2, userId);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("USER NOT UPDATED");
        }
    }

    // UPDATE role (by id)
    public void updateRole(int userId, StaffType role) throws SQLException {
        String sql = String.format("UPDATE %s SET %s = ? WHERE %s = ?", TableNames.USERS, STAFF_TYPE, USER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, role.toString());
            ps.setInt(2, userId);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("USER NOT UPDATED");
        }
    }

    // DELETE (by id)
    public void delete(int userId) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE %s = ?", TableNames.USERS, USER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            int affected = ps.executeUpdate();
            if (affected == 0) throw new SQLException("USER NOT FOUND OR NOT DELETED");
            logger.info("USER DELETED id=" + userId);
        }
    }

    private User map(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt(USER_ID));
        u.setUsername(rs.getString(USERNAME));
        u.setHashedPassword(rs.getString(PASSWORD));
        u.setRole(StaffType.valueOf(rs.getString(STAFF_TYPE)));
        return u;
    }
}