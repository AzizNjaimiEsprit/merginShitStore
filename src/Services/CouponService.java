package Services;

import Beans.Coupon;
import Beans.User;
import Dao.IService;
import Utility.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CouponService implements IService<Coupon> {
    private final Connection cnx = Singleton.getConn();

    private String couponCode () {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#*()-_=+|<.>/?";
        return RandomStringUtils.random( 8, characters );
    }

    // This method used for testing...
    private String getPhoneNumber (int userID) {
        try {
            String request = "SELECT telephone FROM USER WHERE id = ?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, userID);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) return resultSet.getString(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
    @Override
    public void add (Coupon coupon) {

    }

    public Coupon addCoupon (Coupon coupon) {
        try {
            String request = "INSERT INTO COUPON values(?, ?, ?)";
            PreparedStatement statement = cnx.prepareStatement(request);
            String newCoupon = this.couponCode();
            statement.setString(1, newCoupon);
            statement.setInt(2, coupon.getUser().getId());
            statement.setFloat(3, coupon.getAmount());
            coupon.setCode(newCoupon);
            statement.executeUpdate();

            System.out.println("New Coupon created successfully - an SMS was sent to " + coupon.getUser().getTelephone());
            return coupon;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public void update (Coupon coupon) {
        try {
            String request = "UPDATE COUPON SET amount = ? where user_id = ?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setFloat(1, coupon.getAmount());
            statement.setInt(2, coupon.getUser().getId());
            statement.executeUpdate();
            System.out.println("Coupon updated successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void delete (int user_id) {
        try {
            String request = "DELETE FROM COUPON where user_id = ?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setInt(1, user_id);
            statement.executeUpdate();
            System.out.println("Coupon deleted successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public Coupon get (int userID) {
        try {
            String request = "SELECT * from COUPON where user_id = ?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, userID);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return (new Coupon(
                        resultSet.getString(1),
                        new User(resultSet.getInt(2)),
                        resultSet.getFloat(3)
                    )
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public Coupon get(String code) {
        Coupon res = null;
        try {
            String request = "SELECT * FROM COUPON where code =?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setString(1, code);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                res = new Coupon(
                        result.getString(1),
                        new User(result.getInt(2)),
                        result.getFloat(3)
                );
                return res;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return res;
    }

    @Override
    public ArrayList<Coupon> get () {
        ArrayList<Coupon> coupons = new ArrayList<>();
        try {
            String request = "SELECT * FROM COUPON";
            PreparedStatement statement = cnx.prepareStatement(request);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                coupons.add(
                        new Coupon(
                        result.getString(1),
                        new User(result.getInt(2)),
                        result.getFloat(3)
                    )
                );
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return coupons;
    }

    public boolean isCouponUsed (Coupon coupon) {
        try {
            String request = "SELECT * FROM COUPON WHERE code = ?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setString(1, coupon.getCode());
            ResultSet result = statement.executeQuery();
            if (!result.next()) return false;
            else {
                String req = "SELECT * FROM COUPON_USAGE_HISTORY WHERE coupon_code = ? and usage_type = 'PAYMENT'";
                PreparedStatement st = cnx.prepareStatement(req);
                st.setString(1, coupon.getCode());
                ResultSet resultSet = st.executeQuery();
                if (resultSet.next()) return false;
                else return true;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return true;
    }

}
