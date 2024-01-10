package org.example.Classes;


import java.sql.Date;
import java.sql.Time;

/**
 * The type Reservation.
 */
public class Reservation {
    private int reservationId;

    private int tableNumber;

    private Date date;

    private Time startTime;

    private Time endTime;

    /**
     * Instantiates a new Reservation.
     *
     * @param tableNumber the table number
     * @param date        the date
     * @param startTime   the start time
     * @param endTime     the end time
     */
    public Reservation(int tableNumber, Date date, Time startTime, Time endTime) {
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
    public int getReservationId() {
        return reservationId;
    }

    /**
     * Sets reservation id.
     *
     * @param reservationId the reservation id
     */
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    /**
     * Gets table number.
     *
     * @return the table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Sets table number.
     *
     * @param tableNumber the table number
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
