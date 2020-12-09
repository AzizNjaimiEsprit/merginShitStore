package Views.Controllers;

import Beans.Coupon;
import Beans.FidelityCard;
import api.SMS_Service;


import Beans.User;
import Utility.Global;
import Services.CouponService;
import Services.FidelityCardService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.util.ResourceBundle;

public class CouponController extends MenuBarController implements Initializable {

    @FXML
    private TextField fidelityPointsTxtField;

    @FXML
    private Button couponGenerating;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FidelityCardService fidelityCardCRUD = new FidelityCardService();
        fidelityPointsTxtField.setText(Integer.toString(fidelityCardCRUD.get(Global.getCurrentUser().getId()).getPoints()));
        fidelityPointsTxtField.setDisable(true);
    }

    public void handleCouponGnerating () {
        CouponService couponCRUD = new CouponService();
        FidelityCardService fidelityCardCRUD = new FidelityCardService();
        Coupon coupon = couponCRUD.addCoupon(new Coupon(Global.getCurrentUser(), fidelityCardCRUD.get(Global.getCurrentUser().getId()).getPoints() * 10));
        fidelityCardCRUD.update(new FidelityCard(Global.getCurrentUser(), 0));
        SMS_Service sms_service = new SMS_Service();
        sms_service.SendSMSCouponGenerated(coupon);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Coupon generation");
        alert.setHeaderText("Coupon is generated");
        alert.setContentText("An sms will be sent to your phone number.");
        alert.showAndWait();
    }
}
