package org.example.Orders;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Order.
 */
public class Order {
    private int orderId;
    private int tableNumber;
    private int staffId;
    private String time;
    private Double total;
    private boolean isPaid;
    private boolean isCanceled;
    private List<OrderItem> orderItems;

    /**
     * Instantiates a new Order.
     *
     * @param tableNumber the table number
     * @param staffId     the staff id
     */
    public Order(int tableNumber, int staffId) {
        this.tableNumber = tableNumber;
        this.staffId = staffId;
        isCanceled = false;
        isPaid = false;
        orderItems = new ArrayList<>();
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

    /**
     * Gets paid.
     *
     * @return the paid
     */
    public Boolean getPaid() {
        return isPaid;
    }

    /**
     * Sets paid.
     *
     * @param paid the paid
     */
    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * we need to do a check to ensure that the item requested exists before adding
     * we also need to check if the item already exists in the list, if so just add the
     * two quantities together
     */
    public void addToOrderItems(OrderItem orderItem){

        this.orderItems.add(orderItem);
    }
}
