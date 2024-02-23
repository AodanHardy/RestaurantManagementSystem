package org.example.DAO;

import org.example.Classes.Reservation;
import org.example.Constants.ColumnNames;
import org.example.Constants.TableNames;
import org.example.Logging.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.example.Constants.ColumnNames.Reservations.*;

public class ReservationDao {
    Logger logger = new Logger(ReservationDao.class);
    Connection connection;

    public ReservationDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Reservation reservation) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)"
        , TableNames.RESERVATIONS, TABLE_NUMBER, DATE, START_TIME, END_TIME
        );


        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            // Set values for placeholders
            statement.setInt(1, reservation.getTableNumber());
            statement.setDate(2, new java.sql.Date(java.sql.Date.valueOf(reservation.getDate()).getTime()));
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
                    logger.info(String.format("RESERVATION ID: %s SAVED", reservation.getReservationId()));
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
