package Services;

import Beans.Order;
import Beans.User;
import Utility.Global;
import Utility.Singleton;
import api.MailingService;
import api.PaymentService;
import api.SMS_Service;
import com.stripe.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Njaimi Med Aziz
 */

public class OrderService implements IService<Order> {

    private Connection con = Singleton.getConn();
    User currenUser = Global.getCurrentUser();
    MailingService mailingService = new MailingService();
    SMS_Service sms_service = new SMS_Service();
    private OrderItemService crudItem = new OrderItemService();
    private CouponHistoryService couponHistoryService = new CouponHistoryService();
    private CouponService couponService = new CouponService();

    /******************************************** Interface Implementation **********************************************/

    @Override
    public void add(Order order) {
        PaymentService service = new PaymentService();
        Customer c = service.getCustomer(order.getUser().getEmail());

        String pay_id = service.chargeCustomer(c.getId(), (int) order.getTotalPrice() * 100);
        if (pay_id == null) {
            System.out.println("Payment Failed");
            return;
        } else {
            order.setPaymentID(pay_id);
        }

        try {
            PreparedStatement preparedStmt = con.prepareStatement("insert into ORDERS values (NULL,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStmt.setInt(1, order.getUser().getId());
            preparedStmt.setFloat(2, order.getTotalPrice());
            preparedStmt.setString(3, order.getPaymentID());
            preparedStmt.setString(4, LocalDate.now().toString());
            preparedStmt.setString(5, order.getAddress());
            preparedStmt.setInt(6, order.getZipCode());
            preparedStmt.setString(7, order.getNumTel());
            preparedStmt.setString(8, "Non Traite");
            preparedStmt.execute();
            // get id
            ResultSet rs = preparedStmt.getGeneratedKeys();
            int orderid = 0;
            if (rs.next())
                orderid = rs.getInt(1);

            final int oi = orderid;
            System.out.println("Inserted record's ID: " + orderid);
            order.setId(orderid);
            order.setOrderDate(LocalDate.now().toString());
            order.getItems().forEach((item) ->
            {
                item.setOrder(new Order(oi));
                crudItem.add(item);
            });
            mailingService.sendReceiptEmail(order);
            System.out.println("Order Inserted Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void update(Order order) {
        try {
            String sql = "update ORDERS set total_price=?,address=?,zipcode=?,numTel=?,status=? where id=?";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setFloat(1, order.getTotalPrice());
            preparedStmt.setString(2, order.getAddress());
            preparedStmt.setInt(3, order.getZipCode());
            preparedStmt.setString(4, order.getNumTel());
            preparedStmt.setString(5, order.getStatus());
            preparedStmt.setInt(6, order.getId());
            preparedStmt.execute();
            order.getItems().forEach((orderItem -> crudItem.update(orderItem)));
            System.out.println("Order Updated Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        // There is no delete in orders
    }

    @Override
    public Order get(int orderid) {

        Order order = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ORDERS where id=" + orderid);
            if (rs.next()) {
                int id = rs.getInt("id");
                User userId = new User(rs.getInt("user_id"));
                Float totalPrice = rs.getFloat("total_price");
                String paymentID = rs.getString("payment_id");
                String orderDate = rs.getString("order_date");
                String address = rs.getString("address");
                int zipcode = rs.getInt("zipcode");
                String numTel = rs.getString("numTel");
                String status = rs.getString("status");
                order = new Order(id, userId, crudItem.getOrederItems(id), totalPrice, paymentID, orderDate, address, zipcode, numTel, status);
                return order;
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return order;
    }

    @Override
    public ArrayList<Order> get() {
        return getOrders("", "", "");
    }

    /***************************************************************************************************/


    public void editOrderStatus(Order order) {
        try {
            String sql = "update ORDERS set status=? where id=?";
            PreparedStatement preparedStmt = con.prepareStatement(sql);
            preparedStmt.setInt(2, order.getId());
            preparedStmt.setString(1, order.getStatus());
            preparedStmt.execute();
            System.out.println("Order Status Changed Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> getOrders(String etat, String sdate, String edate) {
        ArrayList<Order> res = new ArrayList<Order>();
        Order order = null;
        String sql = sql = "SELECT O.*,full_name FROM ORDERS O join USER U on U.id = O.user_id where 1";
        try {
            Statement stmt = con.createStatement();
            if (Global.getCurrentUser().getRole() == 1)
                sql += " AND O.user_id = " + Global.getCurrentUser().getId();
            if (etat != "")
                sql += " AND status='" + etat + "'";
            if (sdate != "" && edate != "") {
                sql += " AND order_date between '" + sdate + "' AND '" + edate + "'";
            }
            System.out.println(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                User user = new User(rs.getInt("user_id"));
                user.setFullName(rs.getString("full_name"));
                Float totalPrice = rs.getFloat("total_price");
                String paymentID = rs.getString("payment_id");
                String orderDate = rs.getString("order_date");
                String address = rs.getString("address");
                int zipcode = rs.getInt("zipcode");
                String numTel = rs.getString("numTel");
                String status = rs.getString("status");
                order = new Order(id, user, crudItem.getOrederItems(id), totalPrice, paymentID, orderDate, address, zipcode, numTel, status);
                res.add(order);
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return res;
    }


}
