package org.example.api;

import org.example.Orders.Order;
import org.example.DAO.OrderDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderDao orderDao;

    public OrderController(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /** GET /orders */
    @GetMapping
    public List<Order> getAll() throws SQLException {
        return orderDao.getAll();
    }

    /** GET /orders/{id} */
    @GetMapping("/{id}")
    public Order getById(@PathVariable int id) throws SQLException {
        Order o = orderDao.getById(id);
        if (o == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        return o;
    }

    /**
     * POST /orders
     * Body:
     * {
     *   "tableNumber": 3,
     *   "userId": 1,
     *   "orderItems": [
     *       { "itemId": 2, "quantity": 1, "itemPrice": 8.5, "specialRequests": "No onions" },
     *       { "itemId": 4, "quantity": 2, "itemPrice": 2.5 }
     *   ]
     * }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order create(@RequestBody Order order) throws SQLException {
        return orderDao.save(order);
    }

    /** DELETE /orders/{id} */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws SQLException {
        if (orderDao.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
        orderDao.delete(id);
    }
}