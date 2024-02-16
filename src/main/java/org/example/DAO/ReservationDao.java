package org.example.DAO;

import org.example.Classes.Reservation;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationDao {
    Logger logger = new Logger(ReservationDao.class);
    Connection connection;

    public ReservationDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservations (table_number, date, start_time, end_time) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Set values for placeholders
            statement.setInt(1, reservation.getTableNumber());
            statement.setDate(2, new java.sql.Date(reservation.getDate().getTime()));
            statement.setTime(3, new java.sql.Time(reservation.getStartTime().getTime()));
            statement.setTime(4, new java.sql.Time(reservation.getEndTime().getTime()));


            int affectedRows = statement.executeUpdate();

            // check if item was saved
            if (affectedRows == 0) {
                logger.error("RESERVATION NOT SAVED");
            }

            // get next to set id to passed in object
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setReservationId(generatedKeys.getInt(1));
                } else {
                    logger.error("RESERVATION NOT SAVED, NO ID OBTAINED");
                }
            }

        } catch (SQLException e) {
            logger.error("RESERVATION THREW EXCEPTION: "+ e.getMessage());
        }
    }
    public void update(){}

    public void delete(){}
}
