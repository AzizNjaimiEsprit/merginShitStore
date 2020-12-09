package Views.Controllers;

import Utility.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author  Njaimi Med Aziz
 */

public abstract class MenuBarController {
    public Map<String,String> interfacesSizes =  new HashMap<>();

    @FXML
    private MenuBar menuBar;

    public MenuBarController() {
        menuBar.prefWidthProperty().bind(Global.getPrimaryStage().widthProperty());
        //Orders
        interfacesSizes.put("PassOrderPage","915.0/896");
        interfacesSizes.put("ClientOrdersListPage","915.0/1598");
        interfacesSizes.put("AdminOrdersListPage","915.0/1598");
        interfacesSizes.put("PaymentPage","584/712");
        interfacesSizes.put("EditOrderPage","730/896");
        //Books
        interfacesSizes.put("AddOnlineBook","921/1305");
        interfacesSizes.put("Book","837/988");
        interfacesSizes.put("HomeByAziz","965/1330");
        interfacesSizes.put("UpdateBook","647/836");
        //WishList + ....
        interfacesSizes.put("InterfaceWishList","597/907");
        interfacesSizes.put("InterfaceBasket","597/907");
    }

    public void handlemenuclick(ActionEvent actionEvent) {
        String ch = actionEvent.getTarget().toString();
        String id = ch.substring(ch.indexOf("id=")+3,ch.indexOf(","));
        System.out.println(id);
        redirect(id);
    }

    public void redirect (String name){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/"+name+".fxml"));
            Parent root = loader.load();
            Global.getPrimaryStage().getScene().setRoot(root);
            Global.getPrimaryStage().setHeight(getHeight(name));
            Global.getPrimaryStage().setWidth(getWidth(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public double getHeight (String name){
        String height = interfacesSizes.get(name).split("/")[0];
        return Double.parseDouble(height);
    }

    public double getWidth (String name){
        String width = interfacesSizes.get(name).split("/")[1];
        return Double.parseDouble(width);
    }
    public boolean checkRole(String name){
        if(name.indexOf("Admin") != -1){
            if (Global.getCurrentUser().getRole() == 1)
                return true;
            else
                return false;
        }
        else{
            if (Global.getCurrentUser().getRole() == 1)
                return false;
            else
                return true;
        }

    }
}
