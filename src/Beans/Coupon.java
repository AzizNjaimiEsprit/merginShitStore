package Beans;

import java.util.Objects;


public class Coupon {
    private String code;
    private User user;
    private float amount;

    public Coupon(String code) {
        this.code = code;
    }

    public Coupon(String code, User user, float amount) {
        this.code = code;
        this.user = user;
        this.amount = amount;
    }

    public String getCode() {
        return code;
    }

    public User getUser() {
        return user;
    }

    public float getAmount() {
        return amount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coupon coupon = (Coupon) o;
        return Float.compare(coupon.amount, amount) == 0 &&
                Objects.equals(code, coupon.code) &&
                Objects.equals(user, coupon.user);
    }



    @Override
    public String toString() {
        return "Coupon{" +
                "code=" + code +
                ", user=" + user +
                '}';
    }
}
