package Views.Controllers;

import Beans.Client;
import Beans.User;
import Services.UserService;
import Services.clientService;
import Utility.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ClientMenuController extends MenuBarController implements Initializable {
//    @FXML
//    private MenuBar menuBar;

    public Pane ProfilePane;
    public Button UpdateBtn;
    public TextField full_field;
    public TextField phone_field;
    public TextField email_field;
    public TextField address_field;
    public TextField zip_field;
    public TextField login_field;
    public PasswordField pass_field;
    public PasswordField Rpass_field;
    public Button profile;
    public Button logout;
    public AnchorPane AnchorMenu;
    UserService us = new UserService();
    clientService cs = new clientService();
    Client c = new Client();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initMenuBar(menuBar);
        ProfilePane.setVisible(false);
        User cc = Global.getCurrentUser();
        c = cs.GetClient(cc.getId());
        System.out.println("Client is : " + c);
        full_field.setText(c.getFullName());
        phone_field.setText(c.getTelephone());
        email_field.setText(c.getEmail());
        address_field.setText(c.getAddress());
        zip_field.setText(String.valueOf(c.getZipCode()));
        login_field.setText(c.getLogin());
        //pass_field.setText(c.getFullName());

    }

    public boolean testSaisie() {
        if (
                full_field.getText().trim().isEmpty()
                        || phone_field.getText().trim().isEmpty()
                        || email_field.getText().trim().isEmpty()
                        || address_field.getText().trim().isEmpty()
                        || zip_field.getText().trim().isEmpty()
        ) {
            afficherAlert("All fields must be completed!!", Alert.AlertType.ERROR);
            return false;
        }
        Pattern err = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        if (!err.matcher(email_field.getText()).matches()) {
            afficherAlert("Check your email format!!", Alert.AlertType.ERROR);
            return false;
        }
        if (phone_field.getText().length() != 8) {
            afficherAlert("Phone Number must contains 8 numbers", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double num = Double.parseDouble(zip_field.getText());
            Double num1 = Double.parseDouble(phone_field.getText());
        } catch (NumberFormatException e) {
            afficherAlert("Invalid number fields", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    public void UpdateAction(ActionEvent actionEvent) throws SQLException {
        Client c1 = new Client(full_field.getText(), email_field.getText(), phone_field.getText(), login_field.getText(), pass_field.getText(), address_field.getText(), Integer.parseInt(zip_field.getText()));
        if (testSaisie()) {
            if (!us.VerifLogin(login_field.getText(), c.getId())) {
                if (!us.VerifEmail(email_field.getText(), c.getId())) {
                    if (pass_field.getText().equals(Rpass_field.getText())) {
                        if (!pass_field.getText().trim().isEmpty() || !Rpass_field.getText().trim().isEmpty()) {
                            us.UpdateAdmin(c1, c.getId());
                            cs.UpdateClient(address_field.getText(), Integer.parseInt(zip_field.getText()), c.getIdClient());
                            ProfilePane.setVisible(false);
                            afficherAlert("Account: " + c.getFullName() + " successfully updated", Alert.AlertType.INFORMATION);
                        } else {
                            us.UpdateAdmin2(c1, c.getId());
                            cs.UpdateClient(address_field.getText(), Integer.parseInt(zip_field.getText()), c.getIdClient());
                            ProfilePane.setVisible(false);
                            afficherAlert("Account: " + c.getFullName() + " successfully updated", Alert.AlertType.INFORMATION);
                        }
                    } else
                        afficherAlert("Your password field must be equals to Repeat password field", Alert.AlertType.ERROR);
                } else
                    afficherAlert("Email already used", Alert.AlertType.ERROR);
            } else
                afficherAlert("Login already used", Alert.AlertType.ERROR);

        }
    }

    public void ProfileAction(ActionEvent actionEvent) {
        ProfilePane.setVisible(true);
    }

    public void afficherAlert(String message, Alert.AlertType t) {
        Alert alert = new Alert(t);
        alert.setTitle("Information Dialog");
        //alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void LogoutAction(ActionEvent actionEvent) throws IOException {
        Global.setCurrentUser(null);
        ChangeInterface("/Views/Interfaces/login.fxml", "BookStore");
    }

    public void ChangeInterface(String To, String Title) throws IOException {
        Stage stageq = (Stage) AnchorMenu.getScene().getWindow();
        stageq.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(To));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(rootLayout);
        stage.setTitle(Title);
        stage.setScene(scene);
        stage.show();
    }
}
