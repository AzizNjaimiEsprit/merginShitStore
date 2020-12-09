package Beans;

import java.util.Objects;

/**
 * @author Njaimi Med Aziz
 */

public class CouponUsageHistory {
    private Coupon coupon;
    private Order order;
    private String usageType;

    public CouponUsageHistory(Coupon coupon, Order order, String usageType) {
        this.coupon = coupon;
        this.order = order;
        this.usageType = usageType;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CouponUsageHistory that = (CouponUsageHistory) o;
        return Objects.equals(coupon, that.coupon) &&
                Objects.equals(order, that.order) &&
                Objects.equals(usageType, that.usageType);
    }

    @Override
    public String toString() {
        return "CouponUsageHistory{" +
                "coupon=" + coupon.getCode() +
                ", order=" + order.getId() +
                ", usageType='" + usageType + '\'' +
                '}';
    }
}
