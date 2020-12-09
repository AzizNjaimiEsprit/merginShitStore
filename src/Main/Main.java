package Main;

import Services.UserService;
import Utility.Global;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        UserService userSer = new UserService();
        Global.setCurrentUser(userSer.GetUser(3));
        System.out.println(userSer.GetUser(3));
        Parent root = FXMLLoader.load(getClass().getResource("../Views/Interfaces/HomeByAziz.fxml"));
        primaryStage.getIcons().add(new Image("file:src/Views/Resources/images/icon.png"));
        Global.setPrimaryStage(primaryStage);
        Stage ps = Global.getPrimaryStage();
        ps.setTitle("BookStore");
        Scene scene = new Scene(root);
        ps.setScene(scene);
        ps.show();
        //
        System.out.println(primaryStage.getHeight());
        System.out.println(primaryStage.getWidth());
        //
    }

    public static void main(String[] args) {
        launch(args);
    }
}
