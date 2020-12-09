package Views.Controllers;

import Beans.*;
import Services.CrudOnlineBook;
import Services.DaoLibraryImp;
import Utility.Global;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OnlineLibrary  extends MenuBarController implements Initializable {
    @FXML
    private TableView<Library> table_library;

    @FXML
    private TableColumn<Library, OnlineBook> col_book;

    @FXML
    private TableColumn<Library, String> col_resume;

    @FXML
    private TableColumn<Library, String> col_status;

    @FXML
    private TableColumn<Library, Integer> col_page;

    @FXML
    private TableColumn<Library, String> read;

    @FXML
    private TableColumn<Library, String> quiz;

    ArrayList<Button> resumeButton=new ArrayList<>();
    ArrayList<Button> readButton=new ArrayList<>();
    ArrayList<Button> quizButton=new ArrayList<>();

    ObservableList<Library> list= FXCollections.observableArrayList();

    int reachedPage=0;
    DaoLibraryImp daoLibraryImp=new DaoLibraryImp();
    CrudOnlineBook crudOnlineBook=new CrudOnlineBook();
    User user=new User(Global.getCurrentUser().getId());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Library> listM=daoLibraryImp.getLibraryitems(Global.getCurrentUser().getId());
        for(int i=0;i<listM.size();i++){
            resumeButton.add(new Button());
            resumeButton.get(i).getStyleClass().add("buttonBlue");
            resumeButton.get(i).setOnAction(this::handleResumeButtonAction);
            readButton.add(new Button());
            readButton.get(i).getStyleClass().add("buttonBlue");
            readButton.get(i).setOnAction(this::handleReadButtonAction);
            quizButton.add(new Button());
            quizButton.get(i).getStyleClass().add("buttonBlue");
            quizButton.get(i).setOnAction(this::handleQuizButtonAction);
            list.add(new Library(crudOnlineBook.RecupererLivreEnLigneByid(listM.get(i).getBook().getId()),
                    listM.get(i).getStatus(),listM.get(i).getReachedPage(),resumeButton.get(i),readButton.get(i),quizButton.get(i)));
        }
        col_book.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        col_resume.setCellValueFactory(new PropertyValueFactory<Library,String>("resumeButton"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        col_page.setCellValueFactory(new PropertyValueFactory<>("reachedPage"));
        read.setCellValueFactory(new PropertyValueFactory<Library,String>("readButton"));
        quiz.setCellValueFactory(new PropertyValueFactory<Library,String>("quizButton"));
        table_library.setItems(list);

    }

    private void handleReadButtonAction(ActionEvent event){
        try {
            int index=table_library.getSelectionModel().getSelectedIndex();
            String path=table_library.getItems().get(index).getBook().getUrl();
            File file=new File(path);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                    do {
                        reachedPage = Integer.parseInt(JOptionPane.showInputDialog(null, "Please enter the number of reached page"));
                        if(reachedPage>=0 && reachedPage<=table_library.getItems().get(index).getBook().getNbPage()){
                            table_library.getItems().get(index).setReachedPage(reachedPage);

                           String status= daoLibraryImp.updateReachedPage(table_library.getItems().get(index));
                           list.get(index).setStatus(status);
                           list.get(index).setReachedPage(reachedPage);
                            table_library.refresh();
                        }
                        else{
                            System.out.println("Please enter a valid reached page number");
                        }
                    }while(reachedPage<0 || reachedPage>table_library.getItems().get(index).getBook().getNbPage());

                } else {
                  JOptionPane.showMessageDialog(null,"Desktop is not supported!");
                }
            } else {
                JOptionPane.showMessageDialog(null,"File is  unavailable!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleResumeButtonAction(ActionEvent event){
        Voice voice;
        String text;
        try {
            int index = table_library.getSelectionModel().getSelectedIndex();
            text = table_library.getItems().get(index).getBook().getSummary();
            System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
            voice = VoiceManager.getInstance().getVoice("kevin16");
            if (voice != null) {
                voice.allocate();// Allocating Voice
                voice.speak(text);// Calling speak() method
            } else {
                JOptionPane.showMessageDialog(null,"Audio version is Unavailable, please try again later!");
                throw new IllegalStateException("Cannot find voice: kevin16");
            }
        }
        catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void handleQuizButtonAction(ActionEvent event){
        try {
            int index=table_library.getSelectionModel().getSelectedIndex();
            table_library.getItems().get(index).setUser(user);
            if(daoLibraryImp.getLibraryitem(table_library.getItems().get(index)).getQuizScore()==0) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/QuizViewClient.fxml"));
                Parent root = loader.load();
                QuizViewClient quizViewClient = loader.getController();
                quizViewClient.setLibrary(table_library.getItems().get(index));
                Global.getPrimaryStage().getScene().setRoot(root);
                Global.getPrimaryStage().setHeight(getHeight("QuizViewClient"));
                Global.getPrimaryStage().setWidth(getWidth("QuizViewClient"));
            }
            else{
                quizButton.get(index).setDisable(true);
                JOptionPane.showMessageDialog(null,"Quiz already passed!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"This Quiz is Unavailable, please check it later!");
            System.out.println(e.getMessage());
        }
    }



}
