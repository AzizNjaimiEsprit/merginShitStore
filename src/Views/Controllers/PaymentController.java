package Views.Controllers;

import Beans.Coupon;
import Beans.CouponUsageHistory;
import Beans.Order;
import Services.CouponHistoryService;
import Services.OrderService;
import Utility.Global;
import api.PaymentService;
import com.stripe.model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author  Njaimi Med Aziz
 */

public class PaymentController extends MenuBarController implements Initializable {

    Order order;

    Coupon usedCoupn = null;
    OrderService orderService = new OrderService();
    CouponHistoryService couponHistoryService = new CouponHistoryService();
    PaymentService paymentService = new PaymentService();
    InputControlOrders inputControl = new InputControlOrders();

    @FXML
    private TextField in_fullname;

    @FXML
    private TextField in_numCarte;

    @FXML
    private TextField in_month;

    @FXML
    private TextField in_year;

    @FXML
    private TextField in_email;

    @FXML
    private TextField in_amount;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
        in_amount.setText(Float.toString(order.getTotalPrice()));
    }


    public void setUsedCoupn(Coupon usedCoupn) { this.usedCoupn = usedCoupn; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        in_amount.setDisable(true);
    }

    @FXML
    public void checkCard(MouseEvent event) {
        if (!inputControl.checkAddOrder2(in_fullname.getText(),in_numCarte.getText(),in_month.getText(),in_year.getText(),in_email.getText())){
            return;
        }
        boolean res = paymentService.checkValidCard(
                in_fullname.getText(),
                in_email.getText(),
                in_numCarte.getText(),
                Integer.parseInt(in_month.getText()),
                Integer.parseInt(in_year.getText())
        );
        if (!res) {
            inputControl.showAlert("Invalid Card Informations !");
            return;
        } else {
            Customer c = paymentService.getCustomer(in_email.getText());
            if (c.getBalance() < order.getTotalPrice()) {
                JOptionPane.showMessageDialog(null, "Not enough money!");
                return;
            }
            else {
                // Confirmation Dialog
                JLabel label = new JLabel("Do you still want to proceed the payment process?");
                label.setFont(new Font("Arial",Font.BOLD, 16));
                ImageIcon icon = new ImageIcon(new ImageIcon("src/Views/Resources/images/buy-icon.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
                int input = JOptionPane.showConfirmDialog(null,
                        label,
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        icon);
                if (input == JOptionPane.NO_OPTION){
                    return;
                }
                // End Confirmation
                order.getUser().setFullName(in_fullname.getText());
                order.getUser().setEmail(in_email.getText());
                orderService.add(order);
                if (usedCoupn != null){
                    CouponUsageHistory cuh = new CouponUsageHistory(usedCoupn,order,"PAYMENT");
                    couponHistoryService.add(cuh);
                }
                JOptionPane.showMessageDialog(null, "Payment Done !");
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/AdminOrdersListPage.fxml"));
                    Parent root = loader.load();
                    in_email.getScene().setRoot(root);
                    Global.getPrimaryStage().setWidth(getWidth("ClientOrdersListPage"));
                    Global.getPrimaryStage().setHeight(getHeight("ClientOrdersListPage"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
