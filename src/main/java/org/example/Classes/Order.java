package org.example.Classes;

import javax.print.attribute.standard.DateTimeAtCreation;

/**
 * The type Order.
 */
public class Order {
    private int orderId;
    private int tableNumber;
    private int staffId;
    private String time;
    private Double total;

    /**
     * Instantiates a new Order.
     *
     * @param tableNumber the table number
     * @param staffId     the staff id
     */
    public Order(int tableNumber, int staffId) {
        this.tableNumber = tableNumber;
        this.staffId = staffId;
    }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
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
     * Gets staff id.
     *
     * @return the staff id
     */
    public int getStaffId() {
        return staffId;
    }

    /**
     * Sets staff id.
     *
     * @param staffId the staff id
     */
    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Gets total.
     *
     * @return the total
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Sets total.
     *
     * @param total the total
     */
    public void setTotal(Double total) {
        this.total = total;
    }
}
