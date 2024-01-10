package org.example.Classes;

/**
 * The type Menu item.
 */
public class MenuItem {
    private int itemId;

    private String itemName;

    private String description;

    private Double price;

    /**
     * Instantiates a new Menu item.
     *
     * @param itemName    the item name
     * @param description the description
     * @param price       the price
     */
    public MenuItem(String itemName, String description, Double price) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
    }

    /**
     * Instantiates a new Menu item.
     *
     * @param itemName    the item name
     * @param description the description
     */
    public MenuItem(String itemName, String description) {
        this.itemName = itemName;
        this.description = description;
    }

    /**
     * Gets item id.
     *
     * @return the item id
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Sets item id.
     *
     * @param itemId the item id
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * Gets item name.
     *
     * @return the item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets item name.
     *
     * @param itemName the item name
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(Double price) {
        this.price = price;
    }
}
