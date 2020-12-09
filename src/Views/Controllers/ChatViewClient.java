package Views.Controllers;

import Beans.Chat;
import Services.ChatDaoImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatViewClient  extends MenuBarController  implements Initializable {
@FXML
    private MenuBar menuBar;

    @FXML
    private AnchorPane col_body;

    @FXML
    private AnchorPane scroll_question;

    @FXML
    private VBox box_question;

    ObservableList<Chat> list= FXCollections.observableArrayList();
    ChatDaoImp chatDaoImp=new ChatDaoImp();
    ArrayList<Label> labels=new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            list=chatDaoImp.afficherChat();

            for(int i=0;i<list.size();i++){
                Label label=new Label(list.get(i).getQuestion());
                label.setWrapText(true);
                label.setTextAlignment(TextAlignment.JUSTIFY);
                label.setMaxWidth(167);
                labels.add(label);
                String answer=list.get(i).getReponse();
                labels.get(i).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        JOptionPane.showMessageDialog(null,answer);
                    }
                });;
                box_question.getChildren().add(labels.get(i));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
