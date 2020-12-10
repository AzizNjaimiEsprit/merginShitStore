package Views.Controllers;

import Services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VerificationCodeController implements Initializable {

    public Button validate_btn;
    public TextField verifcode_field;
    public Hyperlink hyper;
    public AnchorPane verif;
    public Button back_btn;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        hyper.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //test1.host.showDocument("https://gmail.com");
            }
        });
    }

    public void ValidateAction(ActionEvent actionEvent) throws IOException {
        UserService us = new UserService();
        int c = us.getId(LoginController.k);
        if (c != 0 && LoginController.k != null) {
            us.VerifAccountUser(c, verifcode_field.getText());
            Parent fxml;
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/login.fxml"));
            verif.getChildren().removeAll();
            verif.getChildren().setAll(fxml);
        }
    }

    public void BackAction(ActionEvent actionEvent) throws IOException {
        Parent fxml;
        fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/login.fxml"));
        verif.getChildren().removeAll();
        verif.getChildren().setAll(fxml);
    }
}
