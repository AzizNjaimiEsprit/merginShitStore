package Views.Controllers;


import Beans.OnlineBook;
import Beans.Quiz;
import Beans.User;
import Services.CrudOnlineBook;
import Services.QuizDaoImp;
import Utility.Global;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizViewAdmin extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;

    @FXML
    private TableView<Quiz> table_quiz;

    @FXML
    private TableColumn<Quiz, String> col_book;

    @FXML
    private TableColumn<Quiz, String> col_add;

    @FXML
    private TableColumn<Quiz, String> col_update;

    @FXML
    private TableColumn<Quiz, String> col_remove;

    ArrayList<Button> addButton = new ArrayList<>();
    ArrayList<Button> updateButton = new ArrayList<>();
    ArrayList<Button> removeButton = new ArrayList<>();

    ObservableList<Quiz> list = FXCollections.observableArrayList();

    QuizDaoImp quizDaoImp = new QuizDaoImp();
    CrudOnlineBook crudOnlineBook = new CrudOnlineBook();
    User user = new User(Global.getCurrentUser().getId());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<OnlineBook> listM = crudOnlineBook.RecupererListLivreEnLigne();
        for (int i = 0; i < listM.size(); i++) {
            addButton.add(new Button());
            addButton.get(i).getStyleClass().add("buttonBlue");
            addButton.get(i).setOnAction(this::handleAddButtonAction);
            updateButton.add(new Button());
            updateButton.get(i).getStyleClass().add("buttonBlue");
            updateButton.get(i).setOnAction(this::handleUpdateButtonAction);
            removeButton.add(new Button());
            removeButton.get(i).getStyleClass().add("buttonBlue");
            removeButton.get(i).setOnAction(this::handleRemoveButtonAction);
            list.add(new Quiz(listM.get(i), addButton.get(i), updateButton.get(i), removeButton.get(i)));
        }

        col_book.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        col_add.setCellValueFactory(new PropertyValueFactory<Quiz, String>("addButton"));
        col_update.setCellValueFactory(new PropertyValueFactory<>("updateButton"));
        col_remove.setCellValueFactory(new PropertyValueFactory<>("removeButton"));
        table_quiz.setItems(list);
    }

    private void handleAddButtonAction(ActionEvent actionEvent) {
        try {
            int index = table_quiz.getSelectionModel().getSelectedIndex();
            if (quizDaoImp.getQuizById(table_quiz.getItems().get(index).getOnlineBookId().getId()).isEmpty()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/AddQuizAdmin.fxml"));
                Parent root = loader.load();
                AddQuizAdmin addQuizAdmin = loader.getController();
                addQuizAdmin.setQuiz(table_quiz.getItems().get(index));
                table_quiz.getScene().setRoot(root);
                //Global.getPrimaryStage().getScene().setRoot(root);
                Global.getPrimaryStage().setHeight(getHeight("AddQuizAdmin"));
                Global.getPrimaryStage().setWidth(getWidth("AddQuizAdmin"));
            } else {
                addButton.get(index).setDisable(true);
                updateButton.get(index).setDisable(false);
                removeButton.get(index).setDisable(false);
                JOptionPane.showMessageDialog(null, "Book already has a quiz!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleUpdateButtonAction(ActionEvent actionEvent) {
        try {
            int index = table_quiz.getSelectionModel().getSelectedIndex();
            if (!quizDaoImp.getQuizById(table_quiz.getItems().get(index).getOnlineBookId().getId()).isEmpty()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/UpdateQuizAdmin.fxml"));
                Parent root = loader.load();
                UpdateQuizAdmin updateQuizAdmin = loader.getController();
                updateQuizAdmin.setQuiz(table_quiz.getItems().get(index));
                Global.getPrimaryStage().getScene().setRoot(root);
                Global.getPrimaryStage().setHeight(getHeight("UpdateQuizAdmin"));
                Global.getPrimaryStage().setWidth(getWidth("UpdateQuizAdmin"));
            } else {
                addButton.get(index).setDisable(false);
                updateButton.get(index).setDisable(true);
                removeButton.get(index).setDisable(true);
                JOptionPane.showMessageDialog(null, "There is no quiz for this book, please add one.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleRemoveButtonAction(ActionEvent actionEvent) {
        try {
            int res = JOptionPane.showConfirmDialog(null, "Would you like to delete?", "ConfirmationDelete", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                int index = table_quiz.getSelectionModel().getSelectedIndex();
                if (!quizDaoImp.getQuizById(table_quiz.getItems().get(index).getOnlineBookId().getId()).isEmpty()) {
                    quizDaoImp.deleteQuiz(table_quiz.getItems().get(index));
                    addButton.get(index).setDisable(false);
                    updateButton.get(index).setDisable(true);
                    removeButton.get(index).setDisable(true);
                    JOptionPane.showMessageDialog(null, "Quiz has been removed!");
                } else {
                    addButton.get(index).setDisable(false);
                    updateButton.get(index).setDisable(true);
                    removeButton.get(index).setDisable(true);
                    JOptionPane.showMessageDialog(null, "There is no quiz for this book, please add one.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void handleViewButtonAction(ActionEvent actionEvent) {
    }


}
