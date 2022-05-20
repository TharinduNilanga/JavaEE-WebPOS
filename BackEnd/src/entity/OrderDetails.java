package entity;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class OrderDetails {
    private String oId;
    private String cusId;
    private String itemId;
    private int discount;
    private int quantity;
    private double totalPrice;

    public OrderDetails() {
    }

    public OrderDetails(String oId, String cusId, String itemId, int discount, int quantity, double totalPrice) {
        this.oId = oId;
        this.cusId = cusId;
        this.itemId = itemId;
        this.discount = discount;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "oId='" + oId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", discount=" + discount +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
