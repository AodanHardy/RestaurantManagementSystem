package org.example.DAO;

import org.example.Orders.Order;
import org.example.Orders.OrderItem;

import java.sql.Connection;
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
    Connection connection;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void save(Order order){}

    public void update(Order order){}

    public void delete(){}

    //public List<OrderItem> getOrderItems();


}
