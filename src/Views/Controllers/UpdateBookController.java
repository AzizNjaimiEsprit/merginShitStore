package Views.Controllers;

import Beans.Category;
import Beans.OnlineBook;

import Services.CrudOnlineBook;
import Services.ServiceCategorie;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class UpdateBookController extends MenuBarController implements Initializable {
@FXML
    private MenuBar menuBar;
    private OnlineBook toshow = null;
    private int id =-9;

    public void setToshow(OnlineBook toshow) {
        System.out.println(toshow);
        this.toshow = toshow;
        this.id=toshow.getId();
        init(toshow);
    }



    @FXML
    private TextField txturl;
    @FXML
    private TextField txtitle;

    @FXML
    private TextField txtpubhouse;

    @FXML
    private TextField txtsummary;

    @FXML
    private TextField txtquanity;

    @FXML
    private TextField txtstatus;

    @FXML
    private TextField txtprice;

    @FXML
    private TextField txtpage;

    @FXML
    private TextField txtimage;

    @FXML
    private DatePicker txtdate;

    @FXML
    private ChoiceBox<Category> categories;

    @FXML
    private TextField txtauthor;

    ServiceCategorie cc = new ServiceCategorie();
    @Override
       @Override public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);

        categories.setItems(cc.afficher());


    }
    public void init (OnlineBook toUpdate)
    {
        txtitle.appendText(toUpdate.getTitle());
        txtprice.appendText(Double.toString(toUpdate.getPrice()));
        txtsummary.appendText(toUpdate.getSummary());
        txtquanity.appendText(Integer.toString(toUpdate.getQuantity()));
        txtstatus.appendText(toUpdate.getStatus());
        txtimage.appendText(toUpdate.getImage());
        txtpage.appendText(Integer.toString(toUpdate.getNbPage()));
        txtdate.setValue((toUpdate.getReleaseDate()).toLocalDate());
        txtpubhouse.appendText(toUpdate.getPubHouse());
        categories.setValue(toUpdate.getCategory());
        txtauthor.appendText(toUpdate.getAuthors());
        txturl.appendText(toUpdate.getUrl());
    }
AddOnlineBookController a = new AddOnlineBookController();
    CrudOnlineBook cb = new CrudOnlineBook();
    public void updatebook(javafx.event.ActionEvent actionEvent) {
        OnlineBook book = new OnlineBook(
                txtitle.getText(),
                Double.parseDouble(txtprice.getText()),
                txtpubhouse.getText(),
                txtsummary.getText(),
                Date.valueOf(txtdate.getValue()),
                Integer.parseInt(txtquanity.getText()),
                txtstatus.getText(),
                categories.getSelectionModel().getSelectedItem(),
                txtimage.getText(),
                Integer.parseInt(txtpage.getText()),
                txtauthor.getText(),
                txturl.getText());
        book.setId(id);

        cb.ModifierLivreEnLigne( book);
        JOptionPane.showMessageDialog(null,"Book updated successfully");

    }

    public void backToHome(MouseEvent mouseEvent) {
        redirect("AddOnlineBook");
    }
}
