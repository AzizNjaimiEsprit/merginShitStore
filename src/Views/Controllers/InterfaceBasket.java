package Views.Controllers;

import Beans.Basket;
import Beans.Book;
import Beans.User;
import Services.ServicesBasket;
import Utility.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class InterfaceBasket extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;

    private ObservableList<Basket> data;


    @FXML
    private TableView<Basket> tableviewPanier;

    @FXML
    private TableColumn<Basket, Book> txt_livre;

    @FXML
    private TableColumn<Basket, Integer> txt_quantite;

    @FXML
    private TableColumn<Basket, Book> txt_prix;

    @FXML
    private TableColumn<Basket, Double> txt_prixTotale;

    @FXML
    private TextField BookTextBox;

    @FXML
    private TextField QteTextBox;

    @FXML
    private TextField PriceTextBox;

    @FXML
    private TextField TotalPriceTextBox;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btnValider;

    @FXML
    private TextField textRechercher;


    @FXML
    private Button btnDelete;
    ObservableList<Basket> list = FXCollections.observableArrayList();
    ServicesBasket sb = new ServicesBasket();
    User connectedUser = Global.getCurrentUser();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);
        System.out.println(sb.getBasketOfUser(connectedUser.getId()));
        list = sb.getBasketOfUser(Global.getCurrentUser().getId());
        txt_livre.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        txt_quantite.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        txt_prix.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        txt_prixTotale.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
        tableviewPanier.setItems(list);
    }

    public void onclic(SortEvent<TableView<Basket>> tableViewSortEvent) {

    }

    public void update(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 1) {
            QteTextBox.setDisable(false);
            btnUpdate.setDisable(false);
            Basket b = tableviewPanier.getSelectionModel().getSelectedItem();
            BookTextBox.setText(b.getBookTitle());
            QteTextBox.setText(Integer.toString(b.getQuantity()));
            PriceTextBox.setText(Double.toString(b.getBookPrice()));
            double res = b.getQuantity() * b.getBookPrice();
            TotalPriceTextBox.setText(Double.toString(res));
        } else {


            String newQuantity = JOptionPane.showInputDialog(null, "Enter new quantity");
            try {
                int x = Integer.parseInt(newQuantity);
                if (x <= 0) {
                    JOptionPane.showMessageDialog(null, "Invalide number");
                    return;

                }
                int res = JOptionPane.showConfirmDialog(null, "Would you like to save update?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (res == JOptionPane.YES_OPTION) {
                    tableviewPanier.getSelectionModel().getSelectedItem().setQuantity(x);
                    tableviewPanier.refresh();
                    System.out.println(tableviewPanier.getSelectionModel().getSelectedItem());


                    sb.modifier(tableviewPanier.getSelectionModel().getSelectedItem());

                }
            } catch (Exception ex) {
                System.out.println("Invalide number");
            }


        }

    }


    public void delete(ActionEvent actionEvent) {
        int res = JOptionPane.showConfirmDialog(null, "Would you like to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            tableviewPanier.refresh();

            sb.supprimer(tableviewPanier.getSelectionModel().getSelectedItem());
            list = sb.getBasketOfUser(Global.getCurrentUser().getId());
            tableviewPanier.setItems(list);

        }
    }

    @FXML
    public void updateAction(ActionEvent actionEvent) throws IOException {
        if (QteTextBox.getText().equals("") || QteTextBox.getText().equals("0")) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Quantity must not be empty or equal to zero", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            int qte = Integer.parseInt(QteTextBox.getText());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Quantity must be an integer", ButtonType.OK);
            alert.show();
            return;
        }

        Basket B = tableviewPanier.getSelectionModel().getSelectedItem();

        B.setQuantity(Integer.parseInt(QteTextBox.getText()));
        sb.modifier(tableviewPanier.getSelectionModel().getSelectedItem());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/InterfaceBasket.fxml"));
        Parent root = loader.load();
        tableviewPanier.getScene().setRoot(root);
    }

    @FXML
    void rechercheActionHandler(KeyEvent event) {
        ObservableList<Basket> listRecherche = FXCollections.observableArrayList();
        if (textRechercher.getText() != "") {
            Iterator iterator = list.iterator();
            while (iterator.hasNext()) {
                Basket b = (Basket) iterator.next();
                if (b.getBookTitle().contains(textRechercher.getText())) {
                    listRecherche.add(b);
                }
                tableviewPanier.setItems(listRecherche);
            }
        } else {
            tableviewPanier.setItems(list);
        }
    }

    public void validateBasket(MouseEvent mouseEvent) {
        redirect("PassOrderPage");
    }
}