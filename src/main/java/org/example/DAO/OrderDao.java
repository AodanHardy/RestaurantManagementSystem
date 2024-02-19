package org.example.DAO;

import org.example.Constants.TableNames;
import org.example.Logging.Logger;
import org.example.Orders.Order;
import org.example.Orders.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
        String sql = "INSERT INTO " + TableNames.ORDERS + " (table_number, user_id, total, is_paid, is_canceled) " +
                "VALUES (?, ?,?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, order.getTableNumber());
            statement.setInt(2, order.getUserId());
            statement.setDouble(3, order.getTotal());
            statement.setBoolean(4, order.getPaid());
            statement.setBoolean(5, order.isCanceled());

            int affectedRows = statement.executeUpdate();

            logger.error("ORDER SAVED: ");
            return true;
        } catch (SQLException e) {
            logger.error("ORDER FAILED TO SAVE: " + e.getMessage());
            return false;
        }
    }




    public void update(Order order){}

    public void pay(){}
    public void partPay(){}
    public void deleteOrderItem(){}
    public void deleteOrder(){}


    public void delete(){}

    //public List<OrderItem> getOrderItems();


}
