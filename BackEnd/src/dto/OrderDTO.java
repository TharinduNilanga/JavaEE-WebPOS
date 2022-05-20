package dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * @author Tharindu Nilanga
 * @created 5/20/2022
 */
public class OrderDTO {
    private String oId;
    private String cusId;
    private int discount;
    private double totalPrice;
    private LocalDate date;
    private LocalTime time;
    private ArrayList<OrderDetailsDTO> orderDetail;

    public OrderDTO() {
    }

    public OrderDTO(String oId, String cusId, int discount, double totalPrice, LocalDate date, LocalTime time) {
        this.oId = oId;
        this.cusId = cusId;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.date = date;
        this.time = time;
    }

    public OrderDTO(String oId, String cusId, int discount, double totalPrice, LocalDate date, LocalTime time, ArrayList<OrderDetailsDTO> orderDetail) {
        this.oId = oId;
        this.cusId = cusId;
        this.discount = discount;
        this.totalPrice = totalPrice;
        this.date = date;
        this.time = time;
        this.orderDetail = orderDetail;
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

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }
    public ArrayList<OrderDetailsDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(ArrayList<OrderDetailsDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "oId='" + oId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", discount=" + discount +
                ", totalPrice=" + totalPrice +
                ", date=" + date +
                ", time=" + time +
                ", orderDetail=" + orderDetail +
                '}';
    }
}
