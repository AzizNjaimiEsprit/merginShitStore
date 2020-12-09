package Utility;

import Beans.User;
import Services.UserService;
import javafx.stage.Stage;

public abstract class Global {

    private static User currentUser ;
    private static Stage primaryStage;

    /************************** Getter & Setter *****************************/

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Global.currentUser = currentUser;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        Global.primaryStage = primaryStage;
    }
}
