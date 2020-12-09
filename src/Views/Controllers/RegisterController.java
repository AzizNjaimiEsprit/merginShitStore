package Views.Controllers;

import Services.clientService;
import Services.UserService;
import Beans.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {
@FXML
    private MenuBar menuBar;
    public AnchorPane registerPane;
    public Button register_btn;
    public TextField fullname_field;
    public TextField email_field;
    public TextField phone_field;
    public TextField address_field;
    public TextField zipcode_field;
    public TextField login_field;
    public PasswordField password_field;
    public Button back_btn;
    public PasswordField repeat_field;

    @Override
       @Override public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);

    }
    public boolean testSaisieClient() {
        if (
                fullname_field.getText().trim().isEmpty()
                        || phone_field.getText().trim().isEmpty()
                        || email_field.getText().trim().isEmpty()
                        || address_field.getText().trim().isEmpty()
                        || zipcode_field.getText().trim().isEmpty()
                        || password_field.getText().trim().isEmpty()
                        || repeat_field.getText().trim().isEmpty()
        ) {
            afficherAlert("All fields must be completed!!", Alert.AlertType.ERROR);
            return false;
        }
        Pattern err = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        if(!err.matcher(email_field.getText()).matches())
        {
            afficherAlert("Check your email format!!", Alert.AlertType.ERROR);
            return false;
        }
        if(phone_field.getText().length()!=8)
        {
            afficherAlert("Phone Number must contains 8 numbers", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double num = Double.parseDouble(zipcode_field.getText());
            Double num1 = Double.parseDouble(phone_field.getText());
        } catch (NumberFormatException e) {
            afficherAlert("Invalid number fields", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    public void RegisterAction(ActionEvent actionEvent) throws IOException {
        clientService cs = new clientService();
        UserService us = new UserService();
        if(testSaisieClient()) {
            if (password_field.getText().equals(repeat_field.getText())) {
                Client c = new Client(fullname_field.getText(),email_field.getText(),phone_field.getText(),login_field.getText(),password_field.getText(),address_field.getText(),Integer.parseInt(zipcode_field.getText()));
                us.AddAdmin(c);
                int t = us.getId(login_field.getText());
                Client cc = new Client(t, fullname_field.getText(), email_field.getText(), phone_field.getText(), login_field.getText(), password_field.getText(), address_field.getText(), Integer.parseInt(zipcode_field.getText()));
                cs.AddClient(cc);
                afficherAlert("Account Client created successfully!", Alert.AlertType.INFORMATION);
                Parent fxml;
                fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/login.fxml"));
                registerPane.getChildren().removeAll();
                registerPane.getChildren().setAll(fxml);
            }
            else
                afficherAlert("Your password field must be equals to Repeat password field", Alert.AlertType.ERROR);
        }

    }

    public void BackAction(ActionEvent actionEvent) throws IOException {
        Parent fxml;
        fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/login.fxml"));
        registerPane.getChildren().removeAll();
        registerPane.getChildren().setAll(fxml);
    }
    public void afficherAlert(String message, Alert.AlertType t) {
        Alert alert = new Alert(t);
        alert.setTitle("Information Dialog");
        //alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
