package org.example.DAO;

import org.example.Constants.TableNames;
import static org.example.Constants.ColumnNames.Users.*;
import org.example.Logging.Logger;
import org.example.Users.StaffType;
import org.example.Users.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    Logger logger = new Logger(UserDao.class);
    private final Connection connection;

    // Constructor to receive a database connection
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    // Method to save a user to the database
    public void save(User user) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                TableNames.USERS, USERNAME, PASSWORD, STAFF_TYPE
        );

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPassword());
            statement.setString(3, user.getRole().toString());

            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                logger.info("USER NOT SAVED");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setUserId(generatedKeys.getInt(1));
                    logger.info(String.format("USER ID: %s, USERNAME: %s CREATED", user.getUserId(), user.getUsername()));
                } else {
                    logger.error("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }

    public void update(User user){}

    public void delete(User user){}

    // Method to retrieve a user by username
    public User get(String username) {
        String sql = String.format("SELECT * FROM public.%s WHERE %s = ?",
                TableNames.USERS, USERNAME
                );

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt(USER_ID));
                    user.setUsername(resultSet.getString(USERNAME));
                    user.setPassword(resultSet.getString(PASSWORD));
                    user.setRole(StaffType.valueOf(resultSet.getString(STAFF_TYPE)));
                    return user;
                }
            }
        } catch (SQLException e) {
            logger.error("GET THREW EXCEPTION: " + e.getMessage());
        }

        return null; // Return null if the user with the specified username is not found
    }
}
