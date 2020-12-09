package Views.Controllers;

import Beans.Book;
import Beans.User;
import Beans.WishList;
import Services.CrudBook;
import Services.ServicesWishList;
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

public class InterfaceWishList extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;
    private ObservableList<WishList> data;


    @FXML
    private TableView<WishList> tableview_wl;

    @FXML
    private TableColumn<WishList, Book> txt_b;

    @FXML
    private TableColumn<WishList, Book> txt_p;

    @FXML
    private Button btnId;

    ObservableList<WishList> lists = FXCollections.observableArrayList();
    ServicesWishList sw = new ServicesWishList();
    CrudBook crudBook = new CrudBook();

    User connectedUser = Global.getCurrentUser();

    @FXML
    private Button btnValider;

    @FXML
    private TextField TextRecherche;

    @FXML
    private TextField TextBoxTitle;

    @FXML
    private TextField TextBoxDescription;

    @FXML
    private TextField TextBoxFidilityPoint;

    @FXML
    private TextField TextBoxNombrePages;

    @FXML
    void RechercheActionHandler(KeyEvent event) {
        ObservableList<WishList> listRecherche = FXCollections.observableArrayList();
        if (TextRecherche.getText() != "") {
            Iterator iterator = lists.iterator();
            while (iterator.hasNext()) {
                WishList b = (WishList) iterator.next();
                if (b.getBookTitle().contains(TextRecherche.getText())) {
                    listRecherche.add(b);
                }
                tableview_wl.setItems(listRecherche);
            }
        } else {
            tableview_wl.setItems(lists);
        }
    }

    @FXML
    void DetailsActionHandler(MouseEvent event) {
        if (tableview_wl.getSelectionModel() != null) {
            WishList w = tableview_wl.getSelectionModel().getSelectedItem();
            Book b = crudBook.RecupererLivre(new Book(w.getBook().getId()));
            TextBoxTitle.setText(b.getTitle());
            TextBoxDescription.setText(b.getSummary());
            TextBoxFidilityPoint.setText(Integer.toString(b.getNbPage()));
            TextBoxNombrePages.setText(Integer.toString(b.getNbPage()));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);


        lists = sw.getWishListOfUser(Global.getCurrentUser().getId());
        System.out.println("debugging wishlist *********");
        System.out.println(Global.getCurrentUser());
        System.out.println(lists);
        System.out.println("****************************");
        txt_b.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        txt_p.setCellValueFactory(new PropertyValueFactory<>("bookPrice"));
        tableview_wl.setItems(lists);
    }

    @FXML
    void delete_wl(ActionEvent event) {
        int res = JOptionPane.showConfirmDialog(null, "Would you like to delete?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (res == JOptionPane.YES_OPTION) {
            tableview_wl.refresh();
            sw.supprimer(tableview_wl.getSelectionModel().getSelectedItem());
            lists = sw.getWishListOfUser(Global.getCurrentUser().getId());
            tableview_wl.setItems(lists);

        }
    }

    @FXML
    void naviguerWishListAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/InterfaceBasket.fxml"));
        Parent root = loader.load();
        tableview_wl.getScene().setRoot(root);
    }


    public void naviguerCommandeAction(ActionEvent actionEvent) {
    }
}

