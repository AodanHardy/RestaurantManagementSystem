package org.example.DAO;

import org.example.Constants.ColumnNames;
import org.example.Constants.TableNames;
import org.example.Logging.Logger;
import org.example.Orders.Order;
import org.example.Orders.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.Constants.ColumnNames.OrderItems.ORDER_ID;
import static org.example.Constants.ColumnNames.Orders.*;
import static org.example.Constants.ColumnNames.OrderItems.*;

public class OrderDao {
    Logger logger = new Logger(OrderDao.class);
    private final Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public Order save(Order order) throws SQLException {
        String insertOrderSql = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                TableNames.ORDERS, TABLE_NUMBER, USER_ID, TOTAL, IS_PAID, IS_CANCELED
        );

        connection.setAutoCommit(false); // start transaction
        try (PreparedStatement orderStmt = connection.prepareStatement(insertOrderSql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            orderStmt.setInt(1, order.getTableNumber());
            orderStmt.setInt(2, order.getUserId());
            orderStmt.setDouble(3, order.getTotal() != null ? order.getTotal() : 0.0);
            orderStmt.setBoolean(4, order.getPaid() != null ? order.getPaid() : false);
            orderStmt.setBoolean(5, order.isCanceled());

            int affected = orderStmt.executeUpdate();
            if (affected == 0)
                throw new SQLException("Order not saved (0 rows affected)");

            try (ResultSet keys = orderStmt.getGeneratedKeys()) {
                if (keys.next()) {
                    order.setOrderId(keys.getInt(1));
                } else {
                    throw new SQLException("No order_id returned after insert");
                }
            }

            // Now insert order items
            if (order.getOrderItems() != null && !order.getOrderItems().isEmpty()) {
                String insertItemSql = String.format(
                        "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                        TableNames.ORDER_ITEMS,
                        ColumnNames.OrderItems.ORDER_ID, ITEM_ID, SPECIAL_INSTRUCTIONS, QUANTITY, SUBTOTAL
                );

                for (var item : order.getOrderItems()) {
                    try (PreparedStatement itemStmt = connection.prepareStatement(insertItemSql)) {
                        itemStmt.setInt(1, order.getOrderId());
                        itemStmt.setInt(2, item.getItemId());
                        itemStmt.setString(3, item.getSpecialRequests() != null ? item.getSpecialRequests() : "");
                        itemStmt.setInt(4, item.getQuantity());
                        double price = (item.getItemPrice() != null ? item.getItemPrice() : 0.0);
                        itemStmt.setDouble(5, price * item.getQuantity());
                        itemStmt.executeUpdate();
                    }
                }
            }

            connection.commit();
            return order;

        } catch (SQLException e) {
            connection.rollback();
            e.printStackTrace(); // log the real SQL issue
            throw new SQLException("Failed to save order: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    // READ one
    public Order getById(int id) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TableNames.ORDERS, ORDER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Order order = mapOrder(rs);
                order.setOrderItems(getItemsForOrder(id));
                return order;
            }
        }
    }

    // READ all
    public List<Order> getAll() throws SQLException {
        List<Order> orders = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s ORDER BY %s DESC", TableNames.ORDERS, ORDER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Order order = mapOrder(rs);
                order.setOrderItems(getItemsForOrder(order.getOrderId()));
                orders.add(order);
            }
        }
        return orders;
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String delItems = String.format("DELETE FROM %s WHERE %s = ?", TableNames.ORDER_ITEMS, ORDER_ID);
        String delOrder = String.format("DELETE FROM %s WHERE %s = ?", TableNames.ORDERS, ORDER_ID);

        try (PreparedStatement ps1 = connection.prepareStatement(delItems);
             PreparedStatement ps2 = connection.prepareStatement(delOrder)) {
            ps1.setInt(1, id);
            ps1.executeUpdate();
            ps2.setInt(1, id);
            ps2.executeUpdate();
            logger.info("ORDER DELETED id=" + id);
        }
    }

    private Order mapOrder(ResultSet rs) throws SQLException {
        Order o = new Order();
        o.setOrderId(rs.getInt(ORDER_ID));
        o.setTableNumber(rs.getInt(TABLE_NUMBER));
        o.setUserId(rs.getInt(USER_ID));
        o.setTotal(rs.getDouble(TOTAL));
        o.setPaid(rs.getBoolean(IS_PAID));
        o.setCanceled(rs.getBoolean(IS_CANCELED));
        return o;
    }

    private List<OrderItem> getItemsForOrder(int orderId) throws SQLException {
        List<OrderItem> items = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s WHERE %s = ?", TableNames.ORDER_ITEMS, ORDER_ID);
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem item = new OrderItem();
                    item.setOrderItemId(rs.getInt(ORDER_ITEM_ID));
                    item.setOrderId(orderId);
                    item.setItemId(rs.getInt(ITEM_ID));
                    item.setQuantity(rs.getInt(QUANTITY));
                    item.setSpecialRequests(rs.getString(SPECIAL_INSTRUCTIONS));
                    item.setSubtotal(rs.getDouble(SUBTOTAL));
                    items.add(item);
                }
            }
        }
        return items;
    }
}