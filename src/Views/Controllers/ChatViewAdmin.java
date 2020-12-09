package Views.Controllers;

import Beans.Chat;
import Services.ChatDaoImp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatViewAdmin extends MenuBarController implements Initializable {

    @FXML
    private TableView<Chat> table_chat;

    @FXML
    private TableColumn<Chat, String> col_question;

    @FXML
    private TableColumn<Chat, String> col_answer;

    @FXML
    private TableColumn<Chat, String> col_update;

    ArrayList<Button> updateButton=new ArrayList<>();

    ObservableList<Chat> list= FXCollections.observableArrayList();
    ChatDaoImp chatDaoImp=new ChatDaoImp();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            ObservableList<Chat> listM=chatDaoImp.afficherChat();
            for(int i=0;i<listM.size();i++){
                updateButton.add(new Button());
                updateButton.get(i).getStyleClass().add("buttonBlue");
                updateButton.get(i).setOnAction(this::handleUpdateButtonAction);
                list.add(new Chat(listM.get(i).getQuestion(),listM.get(i).getReponse(),updateButton.get(i)));
            }
            col_question.setCellValueFactory(new PropertyValueFactory<>("question"));
            col_answer.setCellValueFactory(new PropertyValueFactory<>("reponse"));
            col_update.setCellValueFactory(new PropertyValueFactory<Chat,String>("updateButton"));
            table_chat.setItems(list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleUpdateButtonAction(ActionEvent actionEvent) {
        try {
            int index=table_chat.getSelectionModel().getSelectedIndex();
            String question;
            String response;
            do {
                question = JOptionPane.showInputDialog(null, "Please enter a question!");
                if(question.length()>0) {
                    do {
                        response = JOptionPane.showInputDialog(null, "Please enter a response");
                        if (response.length() > 0) {
                            int res = JOptionPane.showConfirmDialog(null,"Would you like to delete?","ConfirmationDelete",JOptionPane.YES_NO_OPTION);
                            if(res==JOptionPane.YES_OPTION) {
                                Chat currentChatItem = chatDaoImp.getChatItem(new Chat(table_chat.getItems().get(index).getQuestion(), table_chat.getItems().get(index).getReponse()));
                                chatDaoImp.modifierChatItem(new Chat(currentChatItem.getId(), question, response));
                                list.get(index).setQuestion(question);
                                list.get(index).setReponse(response);
                                table_chat.refresh();
                                JOptionPane.showMessageDialog(null, "Chat Item has been updated!");
                            }
                        } else {
                            System.out.println("Please enter a valid response");
                        }
                    } while (response.length() <= 0);
                }
                else{
                    System.out.println("Please enter a valid question");
                }
            }while(question.length() <= 0);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterChatItem(ActionEvent event) {
        try {
            String question;
            String response;
            do {
                question = JOptionPane.showInputDialog(null, "Please enter a question!");
                if(question.length()>0) {
                    do {
                        response = JOptionPane.showInputDialog(null, "Please enter a response");
                        if (response.length() > 0) {
                            Chat newChatItem=new Chat(question,response);
                            chatDaoImp.ajouterElementChat(newChatItem);
                            JOptionPane.showMessageDialog(null,"Chat Item has been added!");
                            list.add(newChatItem);
                            table_chat.setItems(list);
                            table_chat.refresh();
                        } else {
                            System.out.println("Please enter a valid response");
                        }
                    } while (response.length() <= 0);
                }
                else{
                    System.out.println("Please enter a valid question");
                }
            }while(question.length() <= 0);
        }catch(Exception e){
          e.printStackTrace();
        }
    }



}
