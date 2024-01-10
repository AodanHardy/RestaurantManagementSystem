package org.example.Users;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The type User.
 */
public class User {
    private int userId;
    private String username;
    private String hashedPassword;
    private StaffType role;

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.hashedPassword = hashPassword(password);
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public StaffType getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(StaffType role) {
        this.role = role;
    }


    /**
     * Check password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
}