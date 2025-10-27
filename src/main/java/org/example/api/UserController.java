package org.example.api;

import org.example.DAO.UserDao;
import org.example.Users.StaffType;
import org.example.Users.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    /** GET /users */
    @GetMapping
    public List<User> getAll() throws SQLException {
        return userDao.getAll();
    }

    /** GET /users/{id} */
    @GetMapping("/{id}")
    public User getById(@PathVariable int id) throws SQLException {
        User u = userDao.getById(id);
        if (u == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        return u;
    }

    /**
     * POST /users
     * Body: { "username": "amy", "password": "secret", "role": "MANAGER" }
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody CreateUserBody body) throws SQLException {
        User u = new User(body.username, StaffType.valueOf(body.role));
        u.setPassword(body.password); // hashes via model
        return userDao.save(u);
    }

    /**
     * PUT /users/{id}/password
     * Body: { "password": "newSecret" }
     */
    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable int id, @RequestBody UpdatePasswordBody body) throws SQLException {
        User existing = userDao.getById(id);
        if (existing == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        User tmp = new User();
        tmp.setPassword(body.password); // hash
        userDao.updatePassword(id, tmp.getHashedPassword());
    }

    /**
     * PUT /users/{id}/role
     * Body: { "role": "CHEF" }
     */
    @PutMapping("/{id}/role")
    public User updateRole(@PathVariable int id, @RequestBody UpdateRoleBody body) throws SQLException {
        User existing = userDao.getById(id);
        if (existing == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        StaffType role = StaffType.valueOf(body.role);
        userDao.updateRole(id, role);
        return userDao.getById(id);
    }

    /** DELETE /users/{id} */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) throws SQLException {
        if (userDao.getById(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        userDao.delete(id);
    }

    /** GET /users/staff-types */
    @GetMapping("/staff-types")
    public StaffType[] getStaffTypes() {
        return StaffType.values();
    }

    // --- small request bodies ---
    public record CreateUserBody(String username, String password, String role) {}
    public record UpdatePasswordBody(String password) {}
    public record UpdateRoleBody(String role) {}
}