package Beans;

import Services.CouponHistoryService;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author  Njaimi Med Aziz
 */

public class Order {
    private int id;
    private User user;
    private ArrayList<OrderItem> items;
    private float totalPrice;
    private String paymentID;
    private String orderDate;
    private String address;
    private int zipCode;
    private String numTel;
    private String status;

    public Order(int id, User user, ArrayList<OrderItem> items, float totalPrice, String paymentID, String orderDate, String address, int zipCode, String numTel, String status) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.totalPrice = totalPrice;
        this.paymentID = paymentID;
        this.orderDate = orderDate;
        this.address = address;
        this.zipCode = zipCode;
        this.numTel = numTel;
        this.status = status;
    }

    public Order(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<OrderItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String paymentID) {
        this.paymentID = paymentID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    // Extra getters used by javafx interfaces
    public int getUserId() { return user.getId(); }
    public String getUserFullName(){return user.getFullName();}
    public float getDiscountCoupon (){
        CouponHistoryService couponHistoryService = new CouponHistoryService();
        CouponUsageHistory x = couponHistoryService.get(id,"PAYMENT");
        if (x != null && x.getUsageType().equals("PAYMENT")){
            return x.getCoupon().getAmount();
        }else
            return 0;
    }
    public float getRefundCoupon (){
        CouponHistoryService couponHistoryService = new CouponHistoryService();
        CouponUsageHistory x = couponHistoryService.get(id,"REFUND");
        if (x != null){
            return x.getCoupon().getAmount();
        }else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", items:`\n" + OrderItem.afficherTab(items) +
                ", totalPrice=" + totalPrice +
                ", paymentID='" + paymentID + '\'' +
                ", orderDate=" + orderDate +
                ", address=" + address +
                ", zipcode=" + zipCode +
                ", numTel=" + numTel +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
