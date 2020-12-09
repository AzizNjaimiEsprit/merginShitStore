package Views.Controllers;;

import Services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgotPasswordController extends MenuBarController implements Initializable {
@FXML
    private MenuBar menuBar;
    public TextField code_field;
    public PasswordField password_field;
    public PasswordField Rpassword_field;
    public Button update_btn;
    public TextField email_field;
    public Button send_btn;
    public Button back_btn;
    public AnchorPane forgotpane;
    public Button valid;
    int ran=0;
    int idd=0;

    @Override
       @Override public void initialize(URL location, ResourceBundle resources) {
        initMenuBar(menuBar);
        password_field.setVisible(false);
        Rpassword_field.setVisible(false);
        update_btn.setVisible(false);
        code_field.setVisible(false);
        send_btn.setVisible(false);
        Code();

    }

    public void UpdateAction(ActionEvent actionEvent) throws IOException {
        UserService us = new UserService();
        if(password_field.getText().equals(Rpassword_field.getText())) {
            us.UpdateRestPassword(ran, password_field.getText(),idd);
            Parent fxml;
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/login.fxml"));
            forgotpane.getChildren().removeAll();
            forgotpane.getChildren().setAll(fxml);
        }
    }

    public void SendAction(ActionEvent actionEvent) {

    }
    public void Code(){
        code_field.textProperty().addListener((observable, oldValue, newValue) -> {
    if(Integer.parseInt(newValue)== UserService.coderest)
    {
        password_field.setVisible(true);
        Rpassword_field.setVisible(true);
        update_btn.setVisible(true);
        code_field.setDisable(true);
        ran=Integer.parseInt(newValue);
    }
        });
    }

    public void BackAction(ActionEvent actionEvent) throws IOException {
        Parent fxml;
        fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/login.fxml"));
        forgotpane.getChildren().removeAll();
        forgotpane.getChildren().setAll(fxml);
    }

    public void ValideAction(ActionEvent actionEvent) {
        UserService us = new UserService();
        idd=us.RestPassword(email_field.getText());
        update_btn.setVisible(false);
        email_field.setDisable(true);
        code_field.setVisible(true);
        valid.setVisible(false);
    }
}
