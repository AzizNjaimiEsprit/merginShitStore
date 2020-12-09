package Views.Controllers;

import Beans.Coupon;
import Beans.CouponUsageHistory;
import Beans.Order;
import Beans.OrderItem;
import Services.CouponHistoryService;
import Services.CouponService;
import Services.OrderItemService;
import Services.OrderService;
import api.SMS_Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Njaimi Med Aziz
 */

public class EditOrderController extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;
    Order toEdit;
    OrderItemService orderItemService = new OrderItemService();
    OrderService orderService = new OrderService();
    CouponService couponService = new CouponService();
    CouponHistoryService historyService = new CouponHistoryService();
    SMS_Service sms_service = new SMS_Service();
    InputControlOrders inputControl = new InputControlOrders();
    private float toRepayValue = 0;
    ObservableList<OrderItem> res = FXCollections.observableArrayList();

    @FXML
    private TableView<OrderItem> order_items;

    @FXML
    private TableColumn<OrderItem, Integer> id_book;

    @FXML
    private TableColumn<OrderItem, String> book_title;

    @FXML
    private TableColumn<OrderItem, Integer> quantity;

    @FXML
    private TableColumn<OrderItem, Float> price;

    @FXML
    private TableColumn<OrderItem, Float> total_price;

    @FXML
    private Label text_totalPrice;

    @FXML
    private TextField in_newQuantity;

    @FXML
    private TextField in_adrs;

    @FXML
    private TextField in_codepostale;

    @FXML
    private TextField in_telephone;


    public Order getToEdit() {
        return toEdit;
    }

    public void setToEdit(Order toEdit) {
        this.toEdit = toEdit;
        res.addAll(toEdit.getItems());
        in_adrs.setText(toEdit.getAddress());
        in_telephone.setText(toEdit.getNumTel());
        in_codepostale.setText("" + toEdit.getZipCode());
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);
        id_book.setCellValueFactory(new PropertyValueFactory("bookId"));
        book_title.setCellValueFactory(new PropertyValueFactory("bookTitle"));
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        price.setCellValueFactory(new PropertyValueFactory("bookPrice"));
        total_price.setCellValueFactory(new PropertyValueFactory("totalBooksPrice"));
        order_items.setItems(res);
        text_totalPrice.setText("0 DT");
    }


    public void valider(MouseEvent mouseEvent) {
        System.out.println("Save Method");
        if (!inputControl.checkAddOrder1(in_adrs.getText(), in_codepostale.getText(), in_telephone.getText())) {
            return;
        }
        toEdit.setAddress(in_adrs.getText());
        toEdit.setNumTel(in_telephone.getText());
        toEdit.setZipCode(Integer.parseInt(in_codepostale.getText()));
        ArrayList<OrderItem> tmp = new ArrayList<>();
        for (OrderItem i : res) {
            tmp.add(i);
        }
        toEdit.setItems(tmp);
        orderService.update(toEdit);
        // Saving coupon and sending email

        Coupon insertedCoupon = couponService.addCoupon(new Coupon(null, toEdit.getUser(), toRepayValue));
        historyService.add(new CouponUsageHistory(insertedCoupon, toEdit, "REFUND"));
        sms_service.SendSMSRefund(toEdit, insertedCoupon);
        // Redirecting
        redirect("ClientOrdersListPage");
    }

    public void clickItem(MouseEvent mouseEvent) {
        OrderItem toEdit;
        if (order_items.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }
        if (mouseEvent.getClickCount() == 2) {
            if (order_items.getSelectionModel().getSelectedItem().getQuantity() == 1) {
                JOptionPane.showMessageDialog(null, "You can't update this item");
                return;
            }
            toEdit = order_items.getSelectionModel().getSelectedItem();
            Integer[] options = new Integer[toEdit.getQuantity() - 1];
            int j = 0;
            for (Integer i = toEdit.getQuantity() - 1; i > 0; i--) {
                options[j++] = i;
            }
            Integer newQ = (Integer) JOptionPane.showInputDialog(null,
                    "Enter the new quantity",
                    "New Quantity",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            // Calcule
            float bookprice = toEdit.getBookPrice();
            int diff = toEdit.getQuantity() - newQ;
            toRepayValue += bookprice * diff;
            text_totalPrice.setText("" + toRepayValue + " Dt");
            order_items.getSelectionModel().getSelectedItem().setQuantity(newQ);
            order_items.refresh();
        }

    }
}
