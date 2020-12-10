package Main;

import Beans.Coupon;
import Services.CouponService;

public class UnitTest {
    public static void main(String[] args) {
        CouponService couponService = new CouponService();
        System.out.println(couponService.isCouponUsed(new Coupon("3IZhQnIC.")));
    }
}
