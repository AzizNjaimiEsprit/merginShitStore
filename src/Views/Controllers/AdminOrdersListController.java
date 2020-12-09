package Views.Controllers;

import Beans.Order;
import Beans.OrderItem;
import Services.OrderService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Njaimi Med Aziz
 */
public class AdminOrdersListController extends MenuBarController implements Initializable {
    ArrayList<String> etats = new ArrayList<>();
    ArrayList<String> etatsFind = new ArrayList<>();
    OrderService orderService = new OrderService();
    float tot = 0;

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
    private TableColumn<OrderItem, Float> tot_price_item;

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

    @FXML
    private TableColumn<Order, String> discount_coupon;

    @FXML
    private TableColumn<Order, String> refund_coupon;

    @FXML
    private Label total_revenue;

    ObservableList<Order> ordersData = FXCollections.observableArrayList(
            orderService.getOrders("", "", "")
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initializing Choice box
        etats.add("Non Traite");
        etats.add("En livraison");
        etats.add("Traite");
        etatsFind.add("All");
        etatsFind.addAll(etats);
        status_select.setItems(FXCollections.observableArrayList(etatsFind));
        status_select.getSelectionModel().select(1);
        this.search(null);
        // Initializing Orders Table
        userId.setCellValueFactory(new PropertyValueFactory("userFullName"));
        totalPrice.setCellValueFactory(new PropertyValueFactory("totalPrice"));
        paymentID.setCellValueFactory(new PropertyValueFactory("paymentID"));
        orderDate.setCellValueFactory(new PropertyValueFactory("orderDate"));
        address.setCellValueFactory(new PropertyValueFactory("address"));
        zipCode.setCellValueFactory(new PropertyValueFactory("zipCode"));
        numTel.setCellValueFactory(new PropertyValueFactory("numTel"));
        status.setCellValueFactory(new PropertyValueFactory("status"));
        discount_coupon.setCellValueFactory(new PropertyValueFactory("discountCoupon"));
        refund_coupon.setCellValueFactory(new PropertyValueFactory("refundCoupon"));
        table.setItems(ordersData);
        updateTotal();
    }

    @FXML
    public void clickItem(MouseEvent event) {
        if (table.getSelectionModel().getSelectedIndex() == -1) {
            return;
        }

        if (event.getClickCount() == 1) //Checking click
        {
            order_items.getItems().clear();
            order_items.refresh();
            book_title.setCellValueFactory(new PropertyValueFactory("bookTitle"));
            quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
            price_item.setCellValueFactory(new PropertyValueFactory("bookPrice"));
            tot_price_item.setCellValueFactory(new PropertyValueFactory("totalBooksPrice"));
            int index = table.getSelectionModel().getSelectedIndex();
            ObservableList<OrderItem> items = FXCollections.observableArrayList(ordersData.get(index).getItems());
            order_items.setItems(items);
        } else if (event.getClickCount() == 2) {
            Order toEdit = table.getSelectionModel().getSelectedItem();
            if (toEdit.getStatus().equals("Traite")) {
                JOptionPane.showMessageDialog(null, "You can't update a treated order status!");
                return;
            }
            Object[] options = etats.toArray();
            String newStatus = (String) JOptionPane.showInputDialog(null,
                    "Enter the new status",
                    "Update Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);
            if (newStatus == null)
                return;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Save ?", "Warning", JOptionPane.OK_CANCEL_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                toEdit.setStatus(newStatus);
                orderService.editOrderStatus(toEdit);
                table.refresh();
            }
        }
    }


    @FXML
    public void search(MouseEvent event) {
        List<Order> filteredList = ordersData;
        Predicate<Order> filterByStatus = p -> (p.getStatus().equals(status_select.getValue()) || status_select.getValue().equals("All"));
        Predicate<Order> filterByDate = p -> (LocalDate.parse(p.getOrderDate()).compareTo(start_date.getValue())>=0 && LocalDate.parse(p.getOrderDate()).compareTo(end_date.getValue())<=0);
        if (start_date.getValue() != null && end_date.getValue() != null) {
            filteredList = filteredList.stream().filter(filterByStatus::test).filter(filterByDate::test).collect(Collectors.toList());
        } else {
            filteredList = filteredList.stream().filter(filterByStatus::test).collect(Collectors.toList());
        }
        table.setItems(FXCollections.observableArrayList(filteredList));
        updateTotal();
    }

    @FXML
    public void resetFilters(MouseEvent event) {
        if (event.getClickCount() == 1) //Checking click
        {
            start_date.setValue(null);
            end_date.setValue(null);
            status_select.getSelectionModel().select(0);
            total_revenue.setText("0 Dt");
            table.setItems(ordersData);
        }
    }


    public void refresh(MouseEvent mouseEvent) {
        ordersData.clear();
        ordersData.addAll(orderService.getOrders("Non Traite", "", ""));
        table.refresh();
        updateTotal();
    }

    public void updateTotal() {
        tot = 0;
        for (Order i : table.getItems()) {
            tot += i.getTotalPrice();
        }
        total_revenue.setText("" + tot + " Dt");
    }

    public void showHints(MouseEvent mouseEvent) {
        JOptionPane.showMessageDialog(null, "To edit order status all you have to do is double click on the order");
    }

}