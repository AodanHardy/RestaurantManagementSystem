package org.example.Classes;

/**
 * The type Table.
 */
public class Table {
    private int tableNumber;
    private int capacity;

    /**
     * Instantiates a new Table.
     */
    public Table() {
    }

    /**
     * Instantiates a new Table.
     *
     * @param tableNumber the table number
     * @param capacity    the capacity
     */
    public Table(int tableNumber, int capacity) {
        this.tableNumber = tableNumber;
        this.capacity = capacity;
    }

    /**
     * Instantiates a new Table.
     *
     * @param tableNumber the table number
     */
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Gets table number.
     *
     * @return the table number
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Sets table number.
     *
     * @param tableNumber the table number
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Gets capacity.
     *
     * @return the capacity
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets capacity.
     *
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
