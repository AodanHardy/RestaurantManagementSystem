package org.example.api;

import org.example.Classes.Table;
import org.example.DAO.TableDao;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/tables")
public class TableController {

    private final TableDao tableDao;

    public TableController(TableDao tableDao) {
        this.tableDao = tableDao;
    }

    /** GET /tables */
    @GetMapping
    public List<Table> getAllTables() throws SQLException {
        return tableDao.getAll();
    }

    /** GET /tables/{tableNumber} */
    @GetMapping("/{tableNumber}")
    public Table getTable(@PathVariable int tableNumber) throws SQLException {
        Table t = tableDao.getById(tableNumber);
        if (t == null) {
            throw new RuntimeException("Table not found");
        }
        return t;
    }

    /**
     * POST /tables
     * Body: { "tableNumber": 3, "capacity": 4 }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Table createTable(@RequestBody Table newTable) throws SQLException {
        return tableDao.save(newTable);
    }

    /**
     * PUT /tables/{tableNumber}
     * Body: { "capacity": 6 }
     */
    @PutMapping("/{tableNumber}")
    public Table updateTable(@PathVariable int tableNumber, @RequestBody Table updated) throws SQLException {
        updated.setTableNumber(tableNumber);
        return tableDao.update(updated);
    }

    /** DELETE /tables/{tableNumber} */
    @DeleteMapping("/{tableNumber}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTable(@PathVariable int tableNumber) throws SQLException {
        tableDao.delete(tableNumber);
    }
}