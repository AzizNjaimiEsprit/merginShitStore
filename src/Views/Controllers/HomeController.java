package Views.Controllers;

import Beans.Book;
import Beans.Category;
import Beans.Chat;
import Beans.OnlineBook;
import Services.*;
import Utility.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HomeController extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;

    CrudBook cb = new CrudBook();
    CrudRate crudRate = new CrudRate();
    ServiceCategorie cc = new ServiceCategorie();

    Comparator<Node> comparator = (n1, n2) -> (n1.getId().compareTo(n2.getId()));
    Predicate<Node> compare = n -> (n.getId().contains("title") || n.getId().contains("author") || n.getId().contains("cover") || n.getId().contains("rate") || n.getId().contains("price"));

    @FXML
    private VBox box_question;

    @FXML
    private AnchorPane childrenHolder;

    @FXML
    private TextField textSearch;
    @FXML
    private ChoiceBox<Category> categorylist;

    @FXML
    private ChoiceBox<String> aaa;

    @FXML
    private ChoiceBox<String> ppp;

    @FXML
    private Label userFullName;


    ObservableList<Book> bookList = FXCollections.observableArrayList(cb.RecupererListLivre());
    private int sliderIterator = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(Global.getCurrentUser().toString());
        userFullName.setText(Global.getCurrentUser().getFullName());
        this.nextSlide(null);
        List<String> list = bookList.stream().map(p -> p.getTitle()).collect(Collectors.toList());
        TextFields.bindAutoCompletion(textSearch, list);
        List<String> listOfauthors = bookList.stream().map(p -> p.getAuthors()).collect(Collectors.toList());
        List<String> listOfPubHouses = bookList.stream().map(p -> p.getPubHouse()).collect(Collectors.toList());
        categorylist.setItems(cc.afficher());
        aaa.setItems(FXCollections.observableArrayList(listOfauthors));
        ppp.setItems(FXCollections.observableArrayList(listOfPubHouses));
        InitFAQ();


        //****************************************************


    }

    public void precSlide(MouseEvent mouseEvent) {

        if (sliderIterator - 4 <= 0) {
            System.out.println("start reached");
            return;
        }
        sliderIterator -= 4;
        List<Book> toShow = bookList.stream().skip(sliderIterator - 4).limit(4).collect(Collectors.toList());
        int i = 0;

        List<Node> nodes = childrenHolder.getChildren().stream().filter(n -> n.getId() != null).filter(compare::test).sorted(comparator).sorted((o1, o2) -> (o1.getId().substring(o1.getId().length() - 1)).compareTo(o2.getId().substring(o2.getId().length() - 1))).collect(Collectors.toList());
        System.out.println("*****************************************************");
        for (Node nodeIn : nodes) {
            if (nodeIn.getId().contains("cover")) {
                Image image = new Image("file:///C:/wamp64/www/BookStore/BooksImage/" + toShow.get(i).getId() + ".jpg");
                ((ImageView) nodeIn).setImage(image);
            }
            if (nodeIn.getId().contains("title")) {
                ((Label) nodeIn).setText(toShow.get(i).getTitle());
            }
            if (nodeIn.getId().contains("author")) {
                ((Label) nodeIn).setText(toShow.get(i).getAuthors());
            }
            if (nodeIn.getId().contains("rate")) {
                ((Label) nodeIn).setText("" + crudRate.getMoyRates(toShow.get(i)) + "%");
            }
            if (nodeIn.getId().contains("price")) {
                ((Label) nodeIn).setText("" + toShow.get(i).getPrice() + "D");
            }
            if (nodeIn.getId().equals("title" + (i + 1))) {
                i++;
            }
        }
    }

    public void nextSlide(MouseEvent mouseEvent) {

        if (sliderIterator + 4 > bookList.size()) {
            System.out.println("end reached");
            return;
        }
        int i = 0;
        List<Book> toShow = bookList.stream().skip(sliderIterator).limit(4).collect(Collectors.toList());
        sliderIterator += 4;
        List<Node> nodes = childrenHolder.getChildren().stream().filter(n -> n.getId() != null).filter(compare::test).sorted(comparator).sorted((o1, o2) -> (o1.getId().substring(o1.getId().length() - 1)).compareTo(o2.getId().substring(o2.getId().length() - 1))).collect(Collectors.toList());

        System.out.println("*****************************************************");
        for (Node nodeIn : nodes) {
            if (nodeIn.getId().contains("cover")) {
                Image image = new Image("file:///C:/wamp64/www/BookStore/BooksImage/" + toShow.get(i).getId() + ".jpg");
                ((ImageView) nodeIn).setImage(image);
                int finalI = i;
                ((ImageView) nodeIn).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 2)
                            redirectToBook( toShow.get(finalI));
                    }
                });
            }
            if (nodeIn.getId().contains("title")) {
                ((Label) nodeIn).setText(toShow.get(i).getTitle());
            }
            if (nodeIn.getId().contains("author")) {
                ((Label) nodeIn).setText(toShow.get(i).getAuthors());
            }
            if (nodeIn.getId().contains("rate")) {
                ((Label) nodeIn).setText("" + crudRate.getMoyRates( toShow.get(i)) + "%");
            }
            if (nodeIn.getId().contains("price")) {
                ((Label) nodeIn).setText("" + toShow.get(i).getPrice() + "D");
            }
            if (nodeIn.getId().equals("title" + (i + 1))) {
                i++;
            }
        }
    }

    public void showBook(MouseEvent mouseEvent) {
        System.out.println(textSearch.getText());
        Book onlineBook = bookList.stream().filter(p -> p.getTitle().equals(textSearch.getText())).findFirst().get();
        redirectToBook(onlineBook);
    }

    // Redirection
    public void redirectToBook(Book book) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/Book.fxml"));
            Global.getPrimaryStage().setWidth(getWidth("Book"));
            Global.getPrimaryStage().setHeight(getHeight("Book"));
            Parent root = loader.load();
            textSearch.getScene().setRoot(root);
            BookController bookController = loader.getController();
            bookController.setToshow(book);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void goToWishlist(MouseEvent mouseEvent) {
        redirect("InterfaceWishList");
    }

    public void goToAccountDetails(MouseEvent mouseEvent) {
        redirect("ClientMenu");
    }

    public void goToBasket(MouseEvent mouseEvent) {
        redirect("InterfaceBasket");

    }

    public void goToOrder(MouseEvent mouseEvent) {
        //    redirect("Order");


    }



    public void InitFAQ() {
        ObservableList<Chat> list = FXCollections.observableArrayList();
        ChatDaoImp chatDaoImp = new ChatDaoImp();
        ArrayList<Label> labels = new ArrayList<>();

        try {
            list = chatDaoImp.afficherChat();

            for (int i = 0; i < list.size(); i++) {
                Label label = new Label(list.get(i).getQuestion());
                label.setWrapText(true);
                label.setTextAlignment(TextAlignment.JUSTIFY);
                label.setMaxWidth(167);
                labels.add(label);
                String answer = list.get(i).getReponse();
                labels.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        JOptionPane.showMessageDialog(null, answer);
                    }
                });
                ;
                box_question.getChildren().add(labels.get(i));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
