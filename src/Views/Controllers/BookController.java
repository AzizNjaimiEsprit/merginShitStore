package Views.Controllers;

import Beans.*;
import Services.CrudComment;
import Services.CrudRate;
import Services.ServicesBasket;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BookController extends MenuBarController implements Initializable {
    CrudComment cc = new CrudComment();
    CrudRate cr = new CrudRate();
    private OnlineBook toshow = null;
    ServicesWishList servicesWishList = new ServicesWishList();
    ServicesBasket servicesBasket = new ServicesBasket();
    OnlineBook b = new OnlineBook();
    Image starImageFull = new Image("file:src/Views/Resources/images/full.png");

    @FXML
    private TextField txtcomment;
    @FXML
    private Label ratenote;

    @FXML
    private TableView<Comment> commentableview;

    @FXML
    private TableColumn<Comment, Integer> username;

    @FXML
    private TableColumn<Comment, String> comment;

    @FXML
    public ImageView bookimage;

    @FXML
    private Label title;

    @FXML
    private Label author;

    @FXML
    private Label category;

    @FXML
    private Label pricebook;

    @FXML
    private Label pubhouse;

    @FXML
    private Label releasedate;

    @FXML
    private Label nbpage;

    @FXML
    private Label priceonlinebook;

    @FXML
    private TextArea summary;

    @FXML
    private ImageView etoile5;

    @FXML
    private ImageView etoile1;

    @FXML
    private ImageView etoile2;

    @FXML
    private ImageView etoile3;

    @FXML
    private ImageView etoile4;

    ArrayList<ImageView> stars = new ArrayList<>();
    boolean rateFlag = true;

    ObservableList<Comment> commentList = null;

    @FXML
    private Label moyrate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stars.add(etoile1);stars.add(etoile2);stars.add(etoile3);stars.add(etoile4);stars.add(etoile5);
        comment.setCellValueFactory(new PropertyValueFactory("text"));
        username.setCellValueFactory(new PropertyValueFactory("userFullName"));

    }


    public void init(OnlineBook b) {
        commentList = FXCollections.observableArrayList(cc.RecupererListComment(b));
        commentableview.setItems(commentList);
        Image image = new Image("file:///C:/wamp64/www/BookStore/BooksImage/"+b.getId()+".jpg");
        bookimage.setImage(image);
        title.setText(b.getTitle());
        author.setText(b.getAuthors());
        category.setText(b.getCategory().getName());
        pubhouse.setText(b.getPubHouse());
        summary.setText(b.getSummary());
        nbpage.setText(String.valueOf(b.getNbPage()));
        pricebook.setText(String.valueOf(b.getPrice()));
        priceonlinebook.setText(String.valueOf(b.getPrice() - b.getPrice() * 0.75));
        moyrate.setText(String.valueOf(cr.getMoyRates(b)));
        Rate rate = cr.RecupererRate(new Rate(0, b, Global.getCurrentUser(), 0));
        if (rate!=null)
        {
            rateFlag=false;
            setStarImages(rate.getRate());
        }


    }


    public void backToHome(MouseEvent mouseEvent) {
        redirect("HomeByAziz");
    }

    public void commentsend(MouseEvent mouseEvent) {
        Comment c = new Comment(0, txtcomment.getText(), toshow, Global.getCurrentUser());
        cc.AjouterCommentaire(c);
        commentList = FXCollections.observableArrayList(cc.RecupererListComment(toshow));
        commentableview.setItems(commentList);
        JOptionPane.showMessageDialog(null,"Thank you for your comment");
        txtcomment.clear();

    }

    public void goToWishlist(ActionEvent actionEvent) {
        WishList wishList =  new WishList(Global.getCurrentUser(),toshow);
        servicesWishList.ajouter(wishList);
        redirect("InterfaceWishList");
    }

    public void goToCard(ActionEvent actionEvent) {
        servicesBasket.ajouter(new Basket(Global.getCurrentUser(),toshow,1));
        redirect("InterfaceBasket");
    }
    //Image image = new Image("../Resources/images/full.png");

    public void setRate1(MouseEvent mouseEvent) {
        if (!rateFlag) return;
        Rate rate = new Rate(0, b, Global.getCurrentUser(), 1);
        cr.AjouterRate(rate);
        ratenote.setText("1");
        setStarImages(1);
        JOptionPane.showMessageDialog(null,"Thank you for your review ");

    }

    public void setRate2(MouseEvent mouseEvent) {
        if (!rateFlag) return;
        Rate rate = new Rate(0, b, Global.getCurrentUser(), 2);
        cr.AjouterRate(rate);
        ratenote.setText("2");
        setStarImages(2);
        JOptionPane.showMessageDialog(null,"Thank you for your review ");

    }

    public void setRate3(MouseEvent mouseEvent) {
        if (!rateFlag) return;
        Rate rate = new Rate(0, b, Global.getCurrentUser(), 3);
        cr.AjouterRate(rate);
        ratenote.setText("3");
        setStarImages(3);
        JOptionPane.showMessageDialog(null,"Thank you for your review ");

    }

    public void setRate4(MouseEvent mouseEvent) {
        if (!rateFlag) return;
        Rate rate = new Rate(0, b, Global.getCurrentUser(), 4);
        cr.AjouterRate(rate);
        ratenote.setText("4");
        setStarImages(4);
        JOptionPane.showMessageDialog(null,"Thank you for your review ");

    }

    public void setRate5(MouseEvent mouseEvent) {
        if (!rateFlag) return;
        Rate rate = new Rate(0, b, Global.getCurrentUser(), 5);
        cr.AjouterRate(rate);
        ratenote.setText("5");
        setStarImages(5);
        JOptionPane.showMessageDialog(null,"Thank you for your review ");

    }
    public void setStarImages(int x){
        rateFlag = false;
        for (int i=0;i<x;i++)
            stars.get(i).setImage(starImageFull);
    }

    public void setToshow(OnlineBook toshow) {
        System.out.println(toshow);
        this.toshow = toshow;
        b.setId(toshow.getId());
        init(toshow);
    }

    public void TSdownload(MouseEvent mouseEvent) {
       File file = new File( "C:/wamp64/www/BookStore/BookTechnicalSheet/"+b.getId()+".pdf");
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
