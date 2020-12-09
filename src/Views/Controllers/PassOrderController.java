package Views.Controllers;

import Beans.Book;
import Beans.Coupon;
import Beans.Order;
import Beans.OrderItem;
import Services.CouponService;
import Services.OrderItemService;
import Services.OrderService;
import Utility.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.MenuItem;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author Njaimi Med Aziz
 */
public class PassOrderController extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;

    OrderItemService orderItemService = new OrderItemService();
    OrderService orderService = new OrderService();
    CouponService couponService = new CouponService();
    Coupon payment_coupon = null;
    InputControlOrders inputControl = new InputControlOrders();
    private float totprix = 0;
    private float amountToPay = 0;
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
    private TextField in_adrs;

    @FXML
    private TextField in_codepostale;

    @FXML
    private TextField in_telephone;

    @FXML
    private Label text_totalPrice;

    @FXML
    private Label label_discount;

    @FXML
    private Label discount_amount;

    @FXML
    private Label amount_toPay;

    @FXML
    private TextField in_coupon;

    @FXML
    private Label label_toPay;

    ObservableList<OrderItem> res = FXCollections.observableArrayList(
            orderItemService.getOrderItemsFromBasket(Global.getCurrentUser().getId())
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);
        label_discount.setVisible(false);
        amount_toPay.setVisible(false);
        discount_amount.setVisible(false);
        label_toPay.setVisible(false);
        id_book.setCellValueFactory(new PropertyValueFactory("bookId"));
        book_title.setCellValueFactory(new PropertyValueFactory("bookTitle"));
        quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        price.setCellValueFactory(new PropertyValueFactory("bookPrice"));
        order_items.setItems(res);
        totprix = 0;
        for (OrderItem i : res) {
            totprix += i.getBookPrice();
        }
        amountToPay = totprix;
        text_totalPrice.setText(Float.toString(totprix) + " DT");

    }

    @FXML
    public void valider(MouseEvent event) {

        String address = in_adrs.getText();
        String codePostal = in_codepostale.getText();
        String numTel = in_telephone.getText();
        // Controle de saisie
        if (inputControl.checkAddOrder1(address, codePostal, numTel) == false)
            return;
        // Preparing confirmation msg
        String text = "<html><hr>Shipment Infos :<br/>Address = " + address + "<br/>ZipCode = " + codePostal + "<br/>Mobile Number = " + numTel;
        text += "<br/><hr>Order Items : ";
        ArrayList<OrderItem> oitems = new ArrayList<>();

        for (OrderItem i : order_items.getItems()) {
            text += "<br/>" + i.getBookTitle() + "    " + i.getQuantity();
            Book tmpb = new Book(i.getBookId());
            tmpb.setTitle(i.getBookTitle());
            oitems.add(new OrderItem(0, null, i.getQuantity(), tmpb));
        }
        text += "<br/><hr>To Pay = " + amountToPay + "</html>";
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", 0, 16));
        // Confirmation Dialog
        ImageIcon icon = new ImageIcon(new ImageIcon("src/Views/Resources/images/pass_order.png").getImage().getScaledInstance(80, 80, Image.SCALE_DEFAULT));
        int input = JOptionPane.showConfirmDialog(null,
                label,
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon);
        if (input == JOptionPane.NO_OPTION) {
            return;
        }
        ////////////////////////////////////////////////////
        Order toInsert = new Order(0, Global.getCurrentUser(), oitems, amountToPay, null, null, address, Integer.parseInt(codePostal), numTel, null);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/PaymentPage.fxml"));
            Global.getPrimaryStage().setWidth(getWidth("PaymentPage"));
            Global.getPrimaryStage().setHeight(getHeight("PaymentPage"));
            Parent root = loader.load();
            in_adrs.getScene().setRoot(root);
            PaymentController paymentController = loader.getController();
            paymentController.setOrder(toInsert);
            if (in_coupon.getText().length() == 8 && discount_amount.isVisible()) {
                paymentController.setUsedCoupn(payment_coupon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private EventHandler<ActionEvent> changeTabPlacement() {
        return new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println(event);
                MenuItem mItem = (MenuItem) event.getSource();
                String side = mItem.getLabel();
                System.out.println(side);
            }
        };
    }

    public void validateCoupon(MouseEvent mouseEvent) {
        label_discount.setVisible(true);
        amount_toPay.setVisible(true);
        discount_amount.setVisible(true);
        label_toPay.setVisible(true);
        if (couponService.isCouponUsed(new Coupon(in_coupon.getText()))) {
            in_coupon.setStyle("-fx-text-box-border: green; -fx-focus-color: green;");
            payment_coupon = couponService.get(in_coupon.getText());
            discount_amount.setText("-" + payment_coupon.getAmount() + " Dt");
            amount_toPay.setText("" + (totprix - payment_coupon.getAmount()) + " Dt");
            amountToPay = totprix - payment_coupon.getAmount();
        } else {
            inputControl.showAlert("Invalid Coupoun");
            in_coupon.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            //in_coupon.clear();
            label_discount.setVisible(false);
            amount_toPay.setVisible(false);
            discount_amount.setVisible(false);
            label_toPay.setVisible(false);
        }
    }
}
