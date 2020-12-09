package Views.Controllers;

import Beans.FidelityCard;
import Beans.Library;
import Beans.Quiz;
import Beans.User;
import Services.AnswerDaoImp;
import Services.QuestionDaoImp;
import Services.QuizDaoImp;
import Services.FidelityCardService;
import Services.DaoLibraryImp;
import Utility.Global;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import javax.swing.*;

public class QuizViewClient extends MenuBarController implements Initializable {

    AnswerDaoImp answerDaoImp=new AnswerDaoImp();
    QuestionDaoImp questionDaoImp=new QuestionDaoImp();
    QuizDaoImp quizDaoImp=new QuizDaoImp();
    DaoLibraryImp daoLibraryImp=new DaoLibraryImp();

    @FXML
    private Button btn_check;

    @FXML
    private Text txt_q1;

    @FXML
    private Text txt_q2;

    @FXML
    private Text txt_q3;

    @FXML
    private RadioButton txt_ans1;

    @FXML
    private RadioButton txt_ans2;

    @FXML
    private RadioButton txt_ans3;

    @FXML
    private RadioButton txt_ans4;

    @FXML
    private RadioButton txt_ans5;

    @FXML
    private RadioButton txt_ans6;

    @FXML
    private RadioButton txt_ans7;

    @FXML
    private RadioButton txt_ans8;

    @FXML
    private RadioButton txt_ans9;

    Library library;
    ObservableList<Quiz> quiz;
    int bookId;
    User connectedUser=new User(Global.getCurrentUser().getId());
    FidelityCardService fidelityCardService=new FidelityCardService();
    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) throws Exception{
            this.library = library;
            bookId = library.getBook().getId();
            quiz = quizDaoImp.getQuizById(bookId);
            if(quiz==null){
                throw new Exception();
            }
            initQuiz(library.getBook().getId());
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initQuiz(int id){
        /* Adding first question */
        txt_q1.setText(quiz.get(0).getQuestion().getQuestion());
        txt_ans1.setText(quiz.get(0).getQuestion().getAnswerId().getFirstAnswer());
        txt_ans2.setText(quiz.get(0).getQuestion().getAnswerId().getSecondAnswer());
        txt_ans3.setText(quiz.get(0).getQuestion().getAnswerId().getThirdAnswer());
        //Adding second question
        txt_q2.setText(quiz.get(1).getQuestion().getQuestion());
        txt_ans4.setText(quiz.get(1).getQuestion().getAnswerId().getFirstAnswer());
        txt_ans5.setText(quiz.get(1).getQuestion().getAnswerId().getSecondAnswer());
        txt_ans6.setText(quiz.get(1).getQuestion().getAnswerId().getThirdAnswer());
        //Adding third question
        txt_q3.setText(quiz.get(2).getQuestion().getQuestion());
        txt_ans7.setText(quiz.get(2).getQuestion().getAnswerId().getFirstAnswer());
        txt_ans8.setText(quiz.get(2).getQuestion().getAnswerId().getSecondAnswer());
        txt_ans9.setText(quiz.get(2).getQuestion().getAnswerId().getThirdAnswer());
    }

    public void checkAnswer(MouseEvent mouseEvent) {
        try {
            int score = 0;
            int correctAnswer1 = quiz.get(0).getQuestion().getAnswerId().getCorrectAnswer();
            int correctAnswer2 = quiz.get(1).getQuestion().getAnswerId().getCorrectAnswer();
            int correctAnswer3 = quiz.get(2).getQuestion().getAnswerId().getCorrectAnswer();

            if (txt_ans1.isSelected() && Integer.parseInt(txt_ans1.getId()) == correctAnswer1)
                score++;
            else if ((txt_ans2.isSelected() && Integer.parseInt(txt_ans2.getId()) == correctAnswer1))
                score++;
            else if ((txt_ans3.isSelected() && Integer.parseInt(txt_ans3.getId()) == correctAnswer1))
                score++;

            if (txt_ans4.isSelected() && Integer.parseInt(txt_ans4.getId()) == correctAnswer2)
                score++;
            else if ((txt_ans5.isSelected() && Integer.parseInt(txt_ans5.getId()) == correctAnswer2))
                score++;
            else if ((txt_ans6.isSelected() && Integer.parseInt(txt_ans6.getId()) == correctAnswer2))
                score++;

            if (txt_ans7.isSelected() && Integer.parseInt(txt_ans7.getId()) == correctAnswer3)
                score++;
            else if ((txt_ans8.isSelected() && Integer.parseInt(txt_ans8.getId()) == correctAnswer3))
                score++;
            else if ((txt_ans9.isSelected() && Integer.parseInt(txt_ans9.getId()) == correctAnswer3))
                score++;
            library.setQuizScore(score);
            daoLibraryImp.sendSms(connectedUser,library);
            daoLibraryImp.updateQuizScore(new Library(connectedUser, library.getBook(), library.getStatus(), library.getReachedPage(), library.getQuizScore()));
            FidelityCard fidelityCard = fidelityCardService.get(connectedUser.getId());
            fidelityCard.setPoints(fidelityCard.getPoints() + score * 10);
            fidelityCardService.update(fidelityCard);
            JOptionPane.showMessageDialog(null, "You scored " + Integer.toString(score) + " out of 3!");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/OnlineLibrary.fxml"));
            Parent root = loader.load();
            OnlineLibrary onlineLibrary = loader.getController();
            Global.getPrimaryStage().getScene().setRoot(root);
            Global.getPrimaryStage().setHeight(getHeight("OnlineLibrary"));
            Global.getPrimaryStage().setWidth(getWidth("OnlineLibrary"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}
