package org.example.Orders;


import java.util.ArrayList;
import java.util.List;

/**
 * The type Order.
 */
public class Order {
    private int orderId;
    private int tableNumber;
    private int userId;
    private String time;
    private Double total;
    private boolean isPaid;
    private boolean isCanceled;
    private List<OrderItem> orderItems;

    /**
     * Instantiates a new Order.
     *
     * @param tableNumber the table number
     * @param userId      the staff id
     */
    public Order(int tableNumber, int userId) {
        this.tableNumber = tableNumber;
        this.userId = userId;
        this.isCanceled = false;
        this.isPaid = false;
        this.orderItems = new ArrayList<>();
        this.total = 0.0;

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
    public int getUserId() {
        return userId;
    }

    /**
     * Sets staff id.
     *
     * @param userId the staff id
     */
    public void setUserId(int userId) {
        this.userId = userId;
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

    /**
     * Is canceled boolean.
     *
     * @return the boolean
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * Sets canceled.
     *
     * @param canceled the canceled
     */
    public void setCanceled(boolean canceled) {
        isCanceled = canceled;
    }

    /**
     * Gets order items.
     *
     * @return the order items
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    /**
     * Sets order items.
     *
     * @param orderItems the order items
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    /**
     * we need to do a check to ensure that the item requested exists before adding
     * we also need to check if the item already exists in the list, if so just add the
     * two quantities together
     *
     * @param orderItem the order item
     */
    public void addToOrderItems(OrderItem orderItem){

        if (orderItem.getItemPrice() != null){
            this.total += orderItem.getItemPrice();
        }

        this.orderItems.add(orderItem);
    }
}
