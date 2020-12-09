package Views.Controllers;

import Services.UserService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import Beans.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Utility.Global;

import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController extends MenuBarController implements Initializable {

    public AnchorPane login;
    public Button login_btn;
    public Button register_btn;
    public Button forgot_password_btn;
    public TextField login_field;
    public Button back_btn;
    public Button back_btn11;
    public Button back_btn1;
    
    public static String k=null;
    public PasswordField password_field;
    public AnchorPane loginPane;
    UserService us = new UserService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RegisterBtn();
        ForgotBtn();
        log();
    }

    public void RegisterBtn() {
        register_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent fxml;
                try {
                    fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/Register.fxml"));
                    login.getChildren().removeAll();
                    login.getChildren().setAll(fxml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void ForgotBtn() {
        forgot_password_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Parent fxml;
                try {
                    fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/ForgotPassword.fxml"));
                    login.getChildren().removeAll();
                    login.getChildren().setAll(fxml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void LoginAction(ActionEvent actionEvent) throws IOException, SQLException {
       // System.out.println(us.LoginIsClient(login_field.getText(),password_field.getText()));
       // System.out.println("password val :  "+password_field.getText());
        if(us.exist(login_field.getText(),password_field.getText())){
        if (us.isVerified(login_field.getText(),password_field.getText())) {
            User loginUser=us.LoginUser(login_field.getText(),password_field.getText());
            Global.setCurrentUser(loginUser);
            if (loginUser!=null) {
                if (loginUser instanceof Client) {
                    redirect("HomeByAziz");
                    //ChangeInterface("/Views/Interfaces/ClientMenu.fxml","BookStore Client Interface");
                }
                else if(loginUser instanceof User) {
                    ChangeInterface("/Views/Interfaces/AdminMenu.fxml","BookStore Admin Interface");
                }
            }
            else
            {
                System.out.println("error");
                afficherAlert("Check your field", Alert.AlertType.ERROR);
            }
        }
        else {
            k=login_field.getText();
            Parent fxml;
            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/VerificationCode.fxml"));
            login.getChildren().removeAll();
            login.getChildren().setAll(fxml);
        }
        }
        else
        {
            System.out.println("error");
            afficherAlert("Not Exist", Alert.AlertType.ERROR);
        }

    }

    public void ChangeInterface(String To,String Title) throws IOException {
        Stage stageq = (Stage) login.getScene().getWindow();
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
    public void afficherAlert(String message, Alert.AlertType t) {
        Alert alert = new Alert(t);
        alert.setTitle("Information Dialog");
        //alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    public void log() {
        password_field.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(us.exist(login_field.getText(),password_field.getText())){
                        if (us.isVerified(login_field.getText(),password_field.getText())) {
                            User loginUser=us.LoginUser(login_field.getText(),password_field.getText());
                            Global.setCurrentUser(loginUser);
                            if (loginUser!=null) {
                                if (loginUser instanceof Client) {
                                    ChangeInterface("/Views/Interfaces/ClientMenu.fxml","BookStore Client Interface");
                                }
                                else if(loginUser instanceof User) {
                                    ChangeInterface("/Views/Interfaces/AdminMenu.fxml","BookStore Admin Interface");
                                }
                            }
                            else
                            {
                                System.out.println("error");
                                afficherAlert("Check your field", Alert.AlertType.ERROR);
                            }
                        }
                        else {
                            k=login_field.getText();
                            Parent fxml;
                            fxml = FXMLLoader.load(getClass().getResource("/Views/Interfaces/VerificationCode.fxml"));
                            login.getChildren().removeAll();
                            login.getChildren().setAll(fxml);
                        }
                    }
                    else
                    {
                        System.out.println("error");
                        afficherAlert("Not Exist", Alert.AlertType.ERROR);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void BackAction(ActionEvent actionEvent) throws IOException {


//        back_btn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                test1.host.showDocument("https://www.facebook.com/nounou.abbes2/");
//            }
//        });
//
//        back_btn11.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                test1.host.showDocument("https://www.instagram.com/nourhene.abbess/");
//            }
//        });
//
//        back_btn1.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                test1.host.showDocument("https://twitter.com/");
//            }
//        });


    }
}
