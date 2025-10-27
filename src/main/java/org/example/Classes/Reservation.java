package org.example.Classes;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Time;
import java.time.LocalDate;

/**
 * The type Reservation.
 */
public class Reservation {

    private int reservationId;
    private int tableNumber;
    private String reservationName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm:ss")
    private Time startTime;

    @JsonFormat(pattern = "HH:mm:ss")
    private Time endTime;

    /**
     * Instantiates a new Reservation.
     */
    public Reservation() {}

    /**
     * Instantiates a new Reservation.
     *
     * @param tableNumber the table number
     * @param name        the name
     * @param date        the date
     * @param startTime   the start time
     * @param endTime     the end time
     */
    public Reservation(int tableNumber, String name, LocalDate date, Time startTime, Time endTime) {
        this.tableNumber = tableNumber;
        this.reservationName = name;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Instantiates a new Reservation.
     *
     * @param id          the id
     * @param tableNumber the table number
     * @param date        the date
     * @param startTime   the start time
     * @param endTime     the end time
     */
    public Reservation(int id, int tableNumber, LocalDate date, Time startTime, Time endTime) {
        this.reservationId = id;
        this.tableNumber = tableNumber;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Gets reservation id.
     *
     * @return the reservation id
     */
    public int getReservationId() { return reservationId; }

    /**
     * Sets reservation id.
     *
     * @param reservationId the reservation id
     */
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    /**
     * Gets table number.
     *
     * @return the table number
     */
    public int getTableNumber() { return tableNumber; }

    /**
     * Sets table number.
     *
     * @param tableNumber the table number
     */
    public void setTableNumber(int tableNumber) { this.tableNumber = tableNumber; }

    /**
     * Gets reservation name.
     *
     * @return the reservation name
     */
    public String getReservationName() { return reservationName; }

    /**
     * Sets reservation name.
     *
     * @param reservationName the reservation name
     */
    public void setReservationName(String reservationName) { this.reservationName = reservationName; }

    /**
     * Gets date.
     *
     * @return the date
     */
    public LocalDate getDate() { return date; }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(LocalDate date) { this.date = date; }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Time getStartTime() { return startTime; }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Time startTime) { this.startTime = startTime; }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Time getEndTime() { return endTime; }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(Time endTime) { this.endTime = endTime; }
}