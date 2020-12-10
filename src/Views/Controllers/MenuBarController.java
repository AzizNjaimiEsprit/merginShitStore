package Views.Controllers;

import Beans.User;
import Utility.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuBar;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Njaimi Med Aziz
 */

public abstract class MenuBarController {
    public Map<String, String> interfacesSizes = new HashMap<>();
    public Map<String, String> adminsInterfaces = new HashMap<>();

    protected void initMenuBar(MenuBar m) {
        m.prefWidthProperty().bind(Global.getPrimaryStage().widthProperty());
    }

    public MenuBarController() {
        //Orders
        interfacesSizes.put("PassOrderPage", "915.0/896");
        interfacesSizes.put("ClientOrdersListPage", "915.0/1598");
        interfacesSizes.put("AdminOrdersListPage", "915.0/1598");
        interfacesSizes.put("PaymentPage", "584/712");
        interfacesSizes.put("PaymentOnlineBook", "584/712");
        interfacesSizes.put("EditOrderPage", "730/896");
        //Books
        interfacesSizes.put("AddOnlineBook", "921/1305");
        interfacesSizes.put("Book", "837/988");
        interfacesSizes.put("HomeByAziz", "965/1330");
        interfacesSizes.put("UpdateBook", "647/836");
        //WishList + ....
        interfacesSizes.put("InterfaceWishList", "597/907");
        interfacesSizes.put("CategoryInterface", "597/907");
        interfacesSizes.put("InterfaceBasket", "597/907");
        //Library
        interfacesSizes.put("OnlineLibrary","545.0/744.0");
        interfacesSizes.put("QuizViewAdmin","563.0/711.0");
        interfacesSizes.put("QuizViewClient","606.0/658.0");
        interfacesSizes.put("ChatViewAdmin","568.0/812.0");
        interfacesSizes.put("ChatViewClient","482.0/812.0");
        interfacesSizes.put("AddQuizAdmin","645.0/692.0");
        interfacesSizes.put("UpdateQuizAdmin","645.0/692.0");
        //user
        interfacesSizes.put("login","527/639");
        interfacesSizes.put("register","439/616");
        interfacesSizes.put("ClientMenu","639/816");
        interfacesSizes.put("AdminMenu","650/816");
        interfacesSizes.put("VerificationCode","439/616");
        interfacesSizes.put("ForgotPassword","439/616");
        // fidelity + coupon + offer
        interfacesSizes.put("OfferList","544/744");
        interfacesSizes.put("Coupon","453/675");
        interfacesSizes.put("OfferDetails","516/708");
        interfacesSizes.put("Offer","609/656");
        //
        adminsInterfaces.put("AddOnlineBook","Admin");
        adminsInterfaces.put("AdminOrdersListPage","Admin");
        adminsInterfaces.put("OfferList","Admin");
        adminsInterfaces.put("UpdateQuizAdmin","Admin");
        adminsInterfaces.put("ChatViewAdmin","Admin");
        adminsInterfaces.put("AddQuizAdmin","Admin");
        adminsInterfaces.put("QuizViewAdmin","Admin");
        adminsInterfaces.put("AdminMenu","Admin");
        adminsInterfaces.put("CategoryInterface","Admin");

    }

    public void handlemenuclick(ActionEvent actionEvent) {
        String ch = actionEvent.getTarget().toString();
        String id = ch.substring(ch.indexOf("id=") + 3, ch.indexOf(","));
        System.out.println(id);
        System.out.println(Global.getCurrentUser().getRole());
        if (Global.getCurrentUser().getRole() == 0 && adminsInterfaces.get(id) != null){
            System.out.println("Access denied");
            return;
        }
        if (id.equals("logout")){
            Global.setCurrentUser(new User());
            redirect("login");
        }
        redirect(id);
    }

    public void redirect(String name) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/" + name + ".fxml"));
            Parent root = loader.load();
            Global.getPrimaryStage().getScene().setRoot(root);
            Global.getPrimaryStage().setHeight(getHeight(name));
            Global.getPrimaryStage().setWidth(getWidth(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getHeight(String name) {
        String height = interfacesSizes.get(name).split("/")[0];
        return Double.parseDouble(height);
    }

    public double getWidth(String name) {
        String width = interfacesSizes.get(name).split("/")[1];
        return Double.parseDouble(width);
    }

    public boolean checkRole(String name) {
        if (name.indexOf("Admin") != -1) {
            if (Global.getCurrentUser().getRole() == 1)
                return true;
            else
                return false;
        } else {
            if (Global.getCurrentUser().getRole() == 1)
                return false;
            else
                return true;
        }

    }
}
