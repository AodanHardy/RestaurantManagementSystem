package org.example.api;

import org.example.Classes.MenuItem;
import org.example.DAO.MenuItemDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/menu-items")
public class MenuItemController {

    private final MenuItemDao menuItemDao;

    public MenuItemController(MenuItemDao menuItemDao) {
        this.menuItemDao = menuItemDao;
    }

    /** GET /menu-items */
    @GetMapping
    public List<MenuItem> getAllItems() throws SQLException {
        return menuItemDao.getAll();
    }

    /** GET /menu-items/{id} */
    @GetMapping("/{id}")
    public MenuItem getItem(@PathVariable int id) throws SQLException {
        MenuItem item = menuItemDao.getById(id);
        if (item == null) {
            throw new RuntimeException("Menu item not found");
        }
        return item;
    }

    /**
     * POST /menu-items
     * Body: { "itemName": "Burger", "description": "Beef burger", "price": 9.50, "active": true }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuItem createItem(@RequestBody MenuItem newItem) throws SQLException {
        return menuItemDao.save(newItem);
    }

    /**
     * PUT /menu-items/{id}
     * Body: { "itemName": "Cheeseburger", "description": "Beef + cheese", "price": 10.50, "active": true }
     */
    @PutMapping("/{id}")
    public MenuItem updateItem(@PathVariable int id, @RequestBody MenuItem updated) throws SQLException {
        updated.setItemId(id);
        return menuItemDao.update(updated);
    }

    /** DELETE /menu-items/{id} */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int id) throws SQLException {
        menuItemDao.delete(id);
    }
}