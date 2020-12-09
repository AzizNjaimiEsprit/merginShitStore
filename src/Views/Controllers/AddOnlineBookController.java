package Views.Controllers;

import Beans.Category;
import Beans.OnlineBook;
import Services.CrudOnlineBook;
import Services.ServiceCategorie;
import Utility.Global;
import api.TechnicalSheetCreation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddOnlineBookController extends MenuBarController implements Initializable {
    ServiceCategorie cc = new ServiceCategorie();
    CrudOnlineBook cb = new CrudOnlineBook();
    TechnicalSheetCreation ts = new TechnicalSheetCreation();

    File img;
    File pdf;
    private int flag = 0;

    @FXML
    private Button addbtn;

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
    private TextField authors;

    @FXML
    private DatePicker txtdate;
    @FXML
    private TextField txturl;

    @FXML
    private ChoiceBox<Category> categories;
    @FXML
    private TableColumn<OnlineBook, String> summary;

    @FXML
    private TableView<OnlineBook> allbookstable;


    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, String> title;

    @FXML
    private TableColumn<OnlineBook, Double> price;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, String> author;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, String> pubHouse;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, Date> releaseDate;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, Integer> quantity;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, String> status;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, Category> category;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, Integer> nbPage;

    @FXML
    private javafx.scene.control.TableColumn<OnlineBook, String> url;

    ObservableList<OnlineBook> bookList = FXCollections.observableArrayList(cb.RecupererListLivreEnLigne());

    private boolean controlnumbers()
    { Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher1 = pattern.matcher(quantity.getText());
        Matcher matcher2 = pattern.matcher(nbPage.getText());
        Matcher matcher3 = pattern.matcher(price.getText());


        if((!matcher1.find())&&(!matcher1.find())&&(!matcher1.find()))
        {
            return true;
        }else {return false; }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setCellValueFactory(new PropertyValueFactory<OnlineBook, String>("title"));
        price.setCellValueFactory(new PropertyValueFactory<OnlineBook, Double>("price"));
        author.setCellValueFactory(new PropertyValueFactory<OnlineBook, String>("authors"));
        pubHouse.setCellValueFactory(new PropertyValueFactory<OnlineBook, String>("pubHouse"));
        releaseDate.setCellValueFactory(new PropertyValueFactory<OnlineBook, Date>("releaseDate"));
        quantity.setCellValueFactory(new PropertyValueFactory<OnlineBook, Integer>("quantity"));
        status.setCellValueFactory(new PropertyValueFactory<OnlineBook, String>("status"));
        category.setCellValueFactory(new PropertyValueFactory<OnlineBook, Category>("category"));
        nbPage.setCellValueFactory(new PropertyValueFactory<OnlineBook, Integer>("nbPage"));
        summary.setCellValueFactory(new PropertyValueFactory<OnlineBook, String>("summary"));
        url.setCellValueFactory(new PropertyValueFactory<OnlineBook, String>("url"));


        allbookstable.setItems(bookList);
        categories.setItems(cc.afficher());

    }

    public void removebook(javafx.event.ActionEvent actionEvent) {
        ObservableList<OnlineBook> allBooks, SingleBook;
        allBooks = allbookstable.getItems();
        SingleBook = allbookstable.getSelectionModel().getSelectedItems();
        SingleBook.forEach(allBooks::remove);
        //Book toDelete = allbookstable.getItems().get(allbookstable.getSelectionModel().getSelectedIndex());


    }


    public void ajouterlivre(javafx.event.ActionEvent actionEvent) {
if(controlnumbers()){

        OnlineBook b = new OnlineBook(
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
                authors.getText(),
                txturl.getText());
   
           int insertId = cb.AjouterLivreEnLigne(b);
           b.setId(insertId);
           System.out.println(b.toString());

           img.renameTo(new File("C:/wamp64/www/BookStore/BooksImage/" + insertId + ".jpg"));
           pdf.renameTo(new File("C:/wamp64/www/BookStore/OnlineBookPdf/" + insertId + ".pdf"));
           JOptionPane.showMessageDialog(null, "Book added successfully");
           bookList = FXCollections.observableArrayList(cb.RecupererListLivreEnLigne());
           allbookstable.setItems(bookList);
           txtitle.clear();
           txtprice.clear();
           txtsummary.clear();
           txtdate.setValue(null);
           txtquanity.clear();
           txtstatus.clear();
           txtimage.clear();
           txtpage.clear();
           txtpubhouse.clear();
           authors.clear();
           txturl.clear();

           ts.CreatTS(b);} else  JOptionPane.showMessageDialog(null,"Invalid quantity , number of pages or price  ");




    }



    public void ToUpdate(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/UpdateBook.fxml"));
            Global.getPrimaryStage().setWidth(getWidth("UpdateBook"));
            Global.getPrimaryStage().setHeight(getHeight("UpdateBook"));
            Parent root = loader.load();
            allbookstable.getScene().setRoot(root);
            UpdateBookController UpdatebookController = loader.getController();
            UpdatebookController.setToshow(allbookstable.getSelectionModel().getSelectedItem());
            System.out.println("done");

        }catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public void pickImage(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        // Set extension filter
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("Image files ","*.jpg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(Global.getPrimaryStage());
        if (file != null) {
            System.out.println(file.getPath());
            txtimage.setText(file.getPath());
            img = file;
            //file.renameTo(new File("C:/wamp64/www/BookStore/BooksImage/text.txt"));
        }
    }

    public void pickPdf(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter =
                new FileChooser.ExtensionFilter("PDF files ","*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(Global.getPrimaryStage());
        if (file != null) {
            System.out.println(file.getPath());
            txturl.setText(file.getPath());
            pdf = file;
            //file.renameTo(new File("C:/wamp64/www/BookStore/BooksImage/text.txt"));
        }
    }
}
