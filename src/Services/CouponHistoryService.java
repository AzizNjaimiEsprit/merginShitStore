package Services;

import Beans.Coupon;
import Beans.CouponUsageHistory;
import Beans.Order;
import Utility.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CouponHistoryService implements IService<CouponUsageHistory> {
    private Connection con = Singleton.getConn();

    @Override
    public void add(CouponUsageHistory couponUsageHistory) {
        try {
            PreparedStatement preparedStmt = con.prepareStatement("insert into COUPON_USAGE_HISTORY values (?,?,?)");
            preparedStmt.setString(1, couponUsageHistory.getCoupon().getCode());
            preparedStmt.setInt(2, couponUsageHistory.getOrder().getId());
            preparedStmt.setString(3, couponUsageHistory.getUsageType());
            preparedStmt.execute();
            System.out.println("Coupon History Inseré Avec Succes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CouponUsageHistory get(int id) {
        return null;
    }

    @Override
    public void delete(int id) {
        // Empty
    }

    @Override
    public void update(CouponUsageHistory couponUsageHistory) {
        // Empty
    }

    @Override
    public ArrayList<CouponUsageHistory> get() {
        ArrayList<CouponUsageHistory> res = new ArrayList<>();
        try {
            PreparedStatement preparedStmt = con.prepareStatement("Select * from COUPON_USAGE_HISTORY");
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getFloat(4));
                res.add(new CouponUsageHistory(
                        new Coupon(rs.getString(1)),
                        new Order(rs.getInt(2)),
                        rs.getString(3)
                ));
            }
            rs.close();
            return res;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public CouponUsageHistory get(int orderId, String type) {

        try {
            PreparedStatement preparedStmt = con.prepareStatement("Select CUH.*,C.amount from COUPON_USAGE_HISTORY CUH JOIN COUPON C on CUH.coupon_code = C.code where order_id=? and usage_type=?");
            preparedStmt.setInt(1, orderId);
            preparedStmt.setString(2, type);
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                return new CouponUsageHistory(
                        new Coupon(rs.getString(1), null, rs.getFloat(4)),
                        new Order(rs.getInt(2)),
                        rs.getString(3)
                );
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public CouponUsageHistory get(String couponCode) {

        try {
            PreparedStatement preparedStmt = con.prepareStatement("Select * from COUPON_USAGE_HISTORY where coupon_code=?");
            preparedStmt.setString(1, couponCode);
            ResultSet rs = preparedStmt.executeQuery();
            if (rs.next()) {
                return new CouponUsageHistory(
                        new Coupon(rs.getString(1)),
                        new Order(rs.getInt(2)),
                        rs.getString(3)
                );
            }
            rs.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

}
