package Views.Controllers;

import Beans.Coupon;
import Beans.Offer;
import Services.CouponService;
import Services.OfferService;
import api.SMS_Service;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class OfferListController extends MenuBarController implements Initializable {

    @FXML
    private TableView<Offer> offersTableView;

    @FXML
    private TableColumn<Offer, String> colUser;

    @FXML
    private TableColumn<Offer, String> bookCol;

    @FXML
    private TableColumn<Offer, String> authorCol;

    @FXML
    private TableColumn<Offer, String> priceCol;

    @FXML
    private TableColumn<Offer, String> submitDateCol;

    @FXML
    private TableColumn<Offer, String> acceptCol;

    @FXML
    private TableColumn<Offer, String> declineOffer;

    ArrayList<Button> acceptButtons = new ArrayList<>();
    ArrayList<Button> declineButtons = new ArrayList<>();

    private OfferService offerCRUD = new OfferService();
    private ObservableList<Offer> offerObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        List<Offer> allOffers = offerCRUD.get().stream().sorted(Comparator.comparing(Offer::getSubmitDate).reversed()).collect(Collectors.toList());

        ObservableList<Offer> list = FXCollections.observableArrayList(allOffers);

        for (int i = 0; i < list.size(); i++) {
            acceptButtons.add(new Button("Accept offer"));
            int finalI = i;
            acceptButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    offerCRUD.buy(list.get(finalI));
                }
            });
            declineButtons.add(new Button("Decline offer"));
            declineButtons.get(i).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    offerCRUD.decline(list.get(finalI));
                }
            });
            offerObservableList.add(new Offer(list.get(i).getId(), list.get(i).getUser(), list.get(i).getTitle(), list.get(i).getDescription(), list.get(i).getAuthor(),
                                    list.get(i).getPrice(),list.get(i).getImage(), list.get(i).getBought(), list.get(i).getSubmitDate(),
                                    acceptButtons.get(i), declineButtons.get(i)));
        }
        colUser.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
        bookCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        acceptCol.setCellValueFactory(new PropertyValueFactory<>("acceptOffer"));
        declineOffer.setCellValueFactory(new PropertyValueFactory<>("declineOffer"));
        offersTableView.setItems(offerObservableList);

        offersTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent)  {
                Offer selectedOffer = offersTableView.getSelectionModel().getSelectedItem();
                FXMLLoader newLoader = new FXMLLoader(getClass().getResource("../Interfaces/OfferDetails.fxml"));
                Stage offerDetailsStage = new Stage();
                Parent root = null;
                try {
                    root = newLoader.load();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                offerDetailsStage.setScene(new Scene(root));
                OfferDetailsController offerDetailsController = newLoader.getController();
                try {
                    offerDetailsController.initData(selectedOffer);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                offerDetailsStage.show();
            }
        });
    }

    private void handleDeclineButton(Offer offer) {
        offerCRUD.decline(offer);
        SMS_Service sms = new SMS_Service();
        sms.SendDeclineSMS(offer.getUser());
    }

    private void handleAcceptButton(Offer offer) {
        offerCRUD.buy(offer);
        SMS_Service sms = new SMS_Service();
        CouponService couponCRUD = new CouponService();
        Coupon coupon = new Coupon(offer.getUser(), (float) (offer.getPrice() * 0.2));
        couponCRUD.add(coupon);
        sms.SendSMSCouponGenerated(coupon);
    }

}
