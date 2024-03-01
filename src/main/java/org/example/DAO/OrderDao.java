package org.example.DAO;

import org.example.Constants.ColumnNames;
import org.example.Constants.TableNames;
import org.example.Logging.Logger;
import org.example.Orders.Order;
import org.example.Orders.OrderItem;

import static org.example.Constants.ColumnNames.Orders.*;
import static org.example.Constants.ColumnNames.OrderItems.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * we need this class to write to both orders and orderItems tables.
 * with each order we will create an entry in the orders table, then we will
 * take each of the orderItems in the order object,
 * and add each of them to the orderItems table.
 * <p>
 * we then need to be able to retrieve an order from the db.
 * get a row in the orders table and map it to the new order object
 * then request any oderItems with that order id, and add them to the
 * list of order items in the order object.
*/
public class OrderDao {
    Logger logger = new Logger(OrderDao.class);
    Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public boolean save(Order order) {
        String sql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                TableNames.ORDERS, TABLE_NUMBER, USER_ID, TOTAL, IS_PAID, IS_CANCELED
        );

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getTableNumber());
            statement.setInt(2, order.getUserId());
            statement.setDouble(3, order.getTotal());
            statement.setBoolean(4, order.getPaid());
            statement.setBoolean(5, order.isCanceled());

            int affectedRows = statement.executeUpdate();


            if (affectedRows == 0) {
                logger.error("ORDER NOT SAVED");
                return false;
            }

            // get next to set id to passed in object
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrderId(generatedKeys.getInt(1));
                } else {
                    logger.error("ODER NOT SAVED, NO ID OBTAINED");
                    return false;
                }
            }
            logger.info("ORDER SAVED: " + order.getOrderId());

            // from here I need to save the order items, then update the total

            String itemSql = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?)",
                    TableNames.ORDER_ITEMS, ColumnNames.OrderItems.ORDER_ID, ITEM_ID, SPECIAL_INSTRUCTIONS, QUANTITY, SUBTOTAL
            );


            for (OrderItem orderItem : order.getOrderItems())
                try (PreparedStatement itemStatement = connection.prepareStatement(itemSql, PreparedStatement.RETURN_GENERATED_KEYS)){
                    itemStatement.setInt(1, order.getOrderId());
                    itemStatement.setInt(2, orderItem.getItemId());
                    itemStatement.setString(3, orderItem.getSpecialRequests());
                    itemStatement.setInt(4, orderItem.getQuantity());
                    itemStatement.setDouble(5, orderItem.getItemPrice() * orderItem.getQuantity());

                    int affectedItemRows = itemStatement.executeUpdate();


                    if (affectedItemRows == 0) {
                        logger.error("ORDER ITEM NOT SAVED: ");
                        return false;
                    }
                }


            return true;
        } catch (SQLException e) {
            logger.error("ORDER FAILED TO SAVE: " + e.getMessage());
            return false;
        }
    }




    public void update(Order order){}

    public void payAll(){}
    public void partPay(){}
    public void deleteOrderItem(){}
    public void deleteOrder(){}

    public void cancelItems(){}


}
