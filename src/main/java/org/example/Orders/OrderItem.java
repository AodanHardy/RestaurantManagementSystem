package org.example.Orders;

/**
 * The type Order item.
 */
public class OrderItem {
    private int orderItemId;
    private int orderId;
    private int itemId;
    private int quantity;
    private String specialRequests;
    private Double itemPrice;
    private Double subtotal;

    /**
     * Instantiates a new Order item.
     */
    public OrderItem() {}

    /**
     * Instantiates a new Order item.
     *
     * @param itemId   the item id
     * @param quantity the quantity
     */
    public OrderItem(int itemId, int quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    /**
     * Instantiates a new Order item.
     *
     * @param itemId   the item id
     * @param quantity the quantity
     * @param price    the price
     */
    public OrderItem(int itemId, int quantity, Double price) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.itemPrice = price;
    }

    /**
     * Gets order item id.
     *
     * @return the order item id
     */
    public int getOrderItemId() { return orderItemId; }

    /**
     * Sets order item id.
     *
     * @param orderItemId the order item id
     */
    public void setOrderItemId(int orderItemId) { this.orderItemId = orderItemId; }

    /**
     * Gets order id.
     *
     * @return the order id
     */
    public int getOrderId() { return orderId; }

    /**
     * Sets order id.
     *
     * @param orderId the order id
     */
    public void setOrderId(int orderId) { this.orderId = orderId; }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public int getItemId() { return itemId; }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(int itemId) { this.itemId = itemId; }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public int getQuantity() { return quantity; }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Gets special requests.
     *
     * @return the special requests
     */
    public String getSpecialRequests() { return specialRequests; }

    /**
     * Sets special requests.
     *
     * @param specialRequests the special requests
     */
    public void setSpecialRequests(String specialRequests) { this.specialRequests = specialRequests; }

    /**
     * Gets item price.
     *
     * @return the item price
     */
    public Double getItemPrice() { return itemPrice; }

    /**
     * Sets item price.
     *
     * @param itemPrice the item price
     */
    public void setItemPrice(Double itemPrice) { this.itemPrice = itemPrice; }

    /**
     * Gets subtotal.
     *
     * @return the subtotal
     */
    public Double getSubtotal() { return subtotal; }

    /**
     * Sets subtotal.
     *
     * @param subtotal the subtotal
     */
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
}