package Views.Controllers;

import Beans.*;
import Dao.DaoLibrary;
import Services.CouponHistoryService;
import Services.DaoLibraryImp;
import Services.OrderService;
import Utility.Global;
import api.PaymentService;
import com.stripe.model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Njaimi Med Aziz
 */

public class PaymentControllerOnlineBook extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;

    PaymentService paymentService = new PaymentService();
    InputControlOrders inputControl = new InputControlOrders();
    OnlineBook toBuy = null;
    DaoLibraryImp daoLibrary = new DaoLibraryImp();

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

    public void setToBuy(OnlineBook toBuy) {
        this.toBuy = toBuy;
        this.toBuy.setPrice(toBuy.getPrice() - toBuy.getPrice()*0.75);
        in_amount.setText(""+this.toBuy.getPrice());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);
        in_amount.setDisable(true);
    }

    @FXML
    public void checkCard(MouseEvent event) {
        if (!inputControl.checkAddOrder2(in_fullname.getText(), in_numCarte.getText(), in_month.getText(), in_year.getText(), in_email.getText())) {
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
            if (c.getBalance() < toBuy.getPrice()) {
                JOptionPane.showMessageDialog(null, "Not enough money!");
                return;
            } else {
                // Confirmation Dialog
                JLabel label = new JLabel("Do you still want to proceed the payment process?");
                label.setFont(new Font("Arial", Font.BOLD, 16));
                ImageIcon icon = new ImageIcon(new ImageIcon("src/Views/Resources/images/buy-icon.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
                int input = JOptionPane.showConfirmDialog(null,
                        label,
                        "Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        icon);
                if (input == JOptionPane.NO_OPTION) {
                    return;
                }
                // End Confirmation

                paymentService.chargeCustomer(paymentService.getCustomer(Global.getCurrentUser().getEmail()).getId(), (int) toBuy.getPrice()*100);
                JOptionPane.showMessageDialog(null, "Payment Done !");
                daoLibrary.addToLibrary(new Library(Global.getCurrentUser(),toBuy,null,0,0));
                redirect("OnlineLibrary");

            }
        }
    }
}
