package entity;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class Item {
    private String itemId;
    private String itemName;
    private double itemPrice;
    private int itemQuantity;

    public Item() {
    }

    public Item(String itemId, String itemName, double itemPrice, int itemQuantity) {
        this.setItemId(itemId);
        this.setItemName(itemName);
        this.setItemPrice(itemPrice);
        this.setItemQuantity(itemQuantity);
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemQuantity=" + itemQuantity +
                '}';
    }
}

