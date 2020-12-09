package Views.Controllers;

import Beans.Answer;
import Beans.OnlineBook;
import Beans.Question;
import Beans.Quiz;
import Services.AnswerDaoImp;
import Services.QuestionDaoImp;
import Services.QuizDaoImp;
import Utility.Global;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


public class AddQuizAdmin extends MenuBarController implements Initializable {
    @FXML
    private MenuBar menuBar;

    @FXML
    private Label txt_title;

    @FXML
    private TextField txt_q1;

    @FXML
    private TextField txt_ans1;

    @FXML
    private TextField txt_ans2;

    @FXML
    private TextField txt_ans3;

    @FXML
    private TextField txt_q2;

    @FXML
    private TextField txt_ans4;

    @FXML
    private TextField txt_ans5;

    @FXML
    private TextField txt_ans6;

    @FXML
    private TextField txt_q3;

    @FXML
    private TextField txt_ans7;

    @FXML
    private TextField txt_ans8;

    @FXML
    private TextField txt_ans9;

    @FXML
    private TextField txt_correct1;

    @FXML
    private TextField txt_correct2;

    @FXML
    private TextField txt_correct3;

    Quiz quiz;
    AnswerDaoImp answerDaoImp = new AnswerDaoImp();
    QuestionDaoImp questionDaoImp = new QuestionDaoImp();
    QuizDaoImp quizDaoImp = new QuizDaoImp();
    int userId = Global.getCurrentUser().getId();
    OnlineBook bookId;
    InputControlLibrary inputControlLibrary = new InputControlLibrary();

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) throws Exception {
        this.quiz = quiz;
        bookId = quiz.getOnlineBookId();
        txt_title.setText(txt_title.getText() + " to the book " + bookId.getTitle());
        txt_title.setMaxWidth(Double.MAX_VALUE);
        txt_title.setAlignment(Pos.CENTER);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addQuiz(ActionEvent actionEvent) {
        try {
            boolean valid = inputControlLibrary.checkQuizInput(txt_q1.getText(), txt_q2.getText(), txt_q3.getText(), txt_ans1.getText(), txt_ans2.getText(),
                    txt_ans3.getText(), txt_ans4.getText(), txt_ans5.getText(), txt_ans6.getText(), txt_ans7.getText(), txt_ans8.getText()
                    , txt_ans9.getText(), txt_correct1.getText(), txt_correct2.getText(), txt_correct3.getText());
            if (valid) {
                //Adding Question1
                Answer newAnswer1 = answerDaoImp.addAnswer(new Answer(txt_ans1.getText(), txt_ans2.getText(), txt_ans3.getText(), Integer.parseInt(txt_correct1.getText())));
                Question newQuestion1 = questionDaoImp.addQuestion(new Question(newAnswer1, txt_q1.getText()));
                quizDaoImp.addQuiz(new Quiz(bookId, newQuestion1));
                //Adding Question2
                Answer newAnswer2 = answerDaoImp.addAnswer(new Answer(txt_ans4.getText(), txt_ans5.getText(), txt_ans6.getText(), Integer.parseInt(txt_correct2.getText())));
                Question newQuestion2 = questionDaoImp.addQuestion(new Question(newAnswer2, txt_q2.getText()));
                quizDaoImp.addQuiz(new Quiz(bookId, newQuestion2));
                //Adding Question3
                Answer newAnswer3 = answerDaoImp.addAnswer(new Answer(txt_ans7.getText(), txt_ans8.getText(), txt_ans9.getText(), Integer.parseInt(txt_correct3.getText())));
                Question newQuestion3 = questionDaoImp.addQuestion(new Question(newAnswer3, txt_q3.getText()));
                quizDaoImp.addQuiz(new Quiz(bookId, newQuestion3));
                JOptionPane.showMessageDialog(null, "Quiz has been added!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/QuizViewAdmin.fxml"));
                Parent root = loader.load();
                Global.getPrimaryStage().getScene().setRoot(root);
                Global.getPrimaryStage().setHeight(getHeight("QuizViewAdmin"));
                Global.getPrimaryStage().setWidth(getWidth("QuizViewAdmin"));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
