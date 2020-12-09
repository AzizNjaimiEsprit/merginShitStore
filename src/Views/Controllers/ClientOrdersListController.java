package Views.Controllers;

import Beans.Order;
import Beans.OrderItem;
import Services.CouponHistoryService;
import Services.OrderService;
import Utility.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ClientOrdersListController extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
    ArrayList<String> etats = new ArrayList<>();
    OrderService orderService = new OrderService();
    CouponHistoryService couponHistoryService = new CouponHistoryService();
    @FXML
    private ChoiceBox<String> status_select;


    @FXML
    private DatePicker end_date;

    @FXML
    private DatePicker start_date;


    @FXML
    private TableView<OrderItem> order_items;


    @FXML
    private TableColumn<OrderItem, String> book_title;

    @FXML
    private TableColumn<OrderItem, Integer> quantity;

    @FXML
    private TableColumn<OrderItem, Float> price_item;

    @FXML
    private TableColumn<OrderItem, Float> total_price_item;

    @FXML
    private TableView<Order> table;


    @FXML
    private TableColumn<Order, Integer> userId;

    @FXML
    private TableColumn<Order, Float> totalPrice;

    @FXML
    private TableColumn<Order, String> paymentID;

    @FXML
    private TableColumn<Order, Date> orderDate;

    @FXML
    private TableColumn<Order, String> address;

    @FXML
    private TableColumn<Order, Integer> zipCode;

    @FXML
    private TableColumn<Order, String> numTel;

    @FXML
    private TableColumn<Order, String> status;

    ObservableList<Order> res = FXCollections.observableArrayList(
            orderService.getOrders("Non Traite", "", "")
    );


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);
        // Initializing Choice box
        etats.add("All");
        etats.add("Non Traite");
        etats.add("En livraison");
        etats.add("Traite");
        status_select.setItems(FXCollections.observableArrayList(etats));
        status_select.getSelectionModel().select(1);
        // Initializing Orders Table
        userId.setCellValueFactory(new PropertyValueFactory("userFullName"));
        totalPrice.setCellValueFactory(new PropertyValueFactory("totalPrice"));
        paymentID.setCellValueFactory(new PropertyValueFactory("paymentID"));
        orderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));
        address.setCellValueFactory(new PropertyValueFactory("address"));
        zipCode.setCellValueFactory(new PropertyValueFactory("zipCode"));
        numTel.setCellValueFactory(new PropertyValueFactory("numTel"));
        status.setCellValueFactory(new PropertyValueFactory("status"));
        table.setItems(res);

    }

    @FXML
    public void clickItem(MouseEvent event) {
        if (order_items.getSelectionModel().getSelectedIndex() == -1)
            return;

        if (event.getClickCount() == 1) //Checking click -> Fetch data into table 2
        {
            order_items.getItems().clear();
            order_items.refresh();
            book_title.setCellValueFactory(new PropertyValueFactory("bookTitle"));
            quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
            price_item.setCellValueFactory(new PropertyValueFactory("bookPrice"));
            total_price_item.setCellValueFactory(new PropertyValueFactory("totalBooksPrice"));
            int index = table.getSelectionModel().getSelectedIndex();
            ObservableList<OrderItem> items = FXCollections.observableArrayList(res.get(index).getItems());
            order_items.setItems(items);
        } else if (event.getClickCount() == 2) { //Checking click*2 -> Editing Order
            if (res.get(table.getSelectionModel().getSelectedIndex()).getStatus().equals("Non Traite") == false) {
                JOptionPane.showMessageDialog(null, "Order cannot be modified!!");
                return;
            }
            if (couponHistoryService.get(res.get(table.getSelectionModel().getSelectedIndex()).getId(), "REFUND") != null) {
                JOptionPane.showMessageDialog(null, "Order already modifier once!!");
                return;
            }
            System.out.println("Updating !!!");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/EditOrderPage.fxml"));
                Global.getPrimaryStage().setWidth(getWidth("EditOrderPage"));
                Global.getPrimaryStage().setHeight(getHeight("EditOrderPage"));
                Parent root = loader.load();
                table.getScene().setRoot(root);
                EditOrderController paymentController = loader.getController();
                int index = table.getSelectionModel().getSelectedIndex();
                paymentController.setToEdit(res.get(index));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void search(MouseEvent event) {
        if (event.getClickCount() == 1) //Checking click
        {
            String status = status_select.getValue().equals("All") ? "" : status_select.getValue();
            if (start_date.getValue() != null && end_date.getValue() != null) {
                res = FXCollections.observableArrayList(orderService.getOrders(status, start_date.getValue().toString(), end_date.getValue().toString()));
            } else {
                res = FXCollections.observableArrayList(orderService.getOrders(status, "", ""));
            }
            table.setItems(res);
        }
    }

    @FXML
    public void resetFilters(MouseEvent event) {
        if (event.getClickCount() == 1) //Checking double click
        {
            start_date.setValue(null);
            end_date.setValue(null);
            status_select.getSelectionModel().select(0);
        }
    }


}