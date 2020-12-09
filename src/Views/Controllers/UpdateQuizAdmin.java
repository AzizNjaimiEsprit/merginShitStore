package Views.Controllers;

import Beans.Answer;
import Beans.OnlineBook;
import Beans.Question;
import Beans.Quiz;
import Services.AnswerDaoImp;
import Services.QuestionDaoImp;
import Services.QuizDaoImp;
import Utility.Global;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;


public class UpdateQuizAdmin extends MenuBarController implements Initializable {
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
    AnswerDaoImp answerDaoImp=new AnswerDaoImp();
    QuestionDaoImp questionDaoImp=new QuestionDaoImp();
    QuizDaoImp quizDaoImp=new QuizDaoImp();
    int userId= Global.getCurrentUser().getId();
    OnlineBook bookId;
    InputControlLibrary inputControlLibrary =new InputControlLibrary();

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) throws Exception {
        this.quiz = quiz;
        bookId=quiz.getOnlineBookId();
        txt_title.setText(txt_title.getText()+" to the book "+bookId.getTitle());
        txt_title.setMaxWidth(Double.MAX_VALUE);
        txt_title.setAlignment(Pos.CENTER);
        initQuiz(quiz);
    }

    private void initQuiz(Quiz quiz) {
       ObservableList<Quiz> existQuiz=quizDaoImp.getQuizById(bookId.getId());
        //Filling question 1
        txt_q1.setText(existQuiz.get(0).getQuestion().getQuestion());
        txt_ans1.setText(existQuiz.get(0).getQuestion().getAnswerId().getFirstAnswer());
        txt_ans2.setText(existQuiz.get(0).getQuestion().getAnswerId().getSecondAnswer());
        txt_ans3.setText(existQuiz.get(0).getQuestion().getAnswerId().getThirdAnswer());
        txt_correct1.setText(Integer.toString(existQuiz.get(0).getQuestion().getAnswerId().getCorrectAnswer()));
        //Filling question 2
        txt_q2.setText(existQuiz.get(1).getQuestion().getQuestion());
        txt_ans4.setText(existQuiz.get(1).getQuestion().getAnswerId().getFirstAnswer());
        txt_ans5.setText(existQuiz.get(1).getQuestion().getAnswerId().getSecondAnswer());
        txt_ans6.setText(existQuiz.get(1).getQuestion().getAnswerId().getThirdAnswer());
        txt_correct2.setText(Integer.toString(existQuiz.get(1).getQuestion().getAnswerId().getCorrectAnswer()));
        //Filling question 3
        txt_q3.setText(existQuiz.get(2).getQuestion().getQuestion());
        txt_ans7.setText(existQuiz.get(2).getQuestion().getAnswerId().getFirstAnswer());
        txt_ans8.setText(existQuiz.get(2).getQuestion().getAnswerId().getSecondAnswer());
        txt_ans9.setText(existQuiz.get(2).getQuestion().getAnswerId().getThirdAnswer());
        txt_correct3.setText(Integer.toString(existQuiz.get(2).getQuestion().getAnswerId().getCorrectAnswer()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    void updateQuiz(ActionEvent event) {
        try {
            boolean valid= inputControlLibrary.checkQuizInput(txt_q1.getText(),txt_q2.getText(),txt_q3.getText(),txt_ans1.getText(),txt_ans2.getText(),
                    txt_ans3.getText(),txt_ans4.getText(),txt_ans5.getText(),txt_ans6.getText(),txt_ans7.getText(),txt_ans8.getText()
                    ,txt_ans9.getText(),txt_correct1.getText(),txt_correct2.getText(),txt_correct3.getText());
            if (valid) {
                ObservableList<Quiz> existQuiz = quizDaoImp.getQuizById(bookId.getId());
                //Adding Question1
                answerDaoImp.editAnswer(new Answer(existQuiz.get(0).getQuestion().getAnswerId().getId(), txt_ans1.getText(), txt_ans2.getText(), txt_ans3.getText(), Integer.parseInt(txt_correct1.getText())));
                questionDaoImp.editQuestion(new Question(existQuiz.get(0).getQuestion().getId(), existQuiz.get(0).getQuestion().getAnswerId(), txt_q1.getText()));
                //Adding Question2
                answerDaoImp.editAnswer(new Answer(existQuiz.get(1).getQuestion().getAnswerId().getId(), txt_ans4.getText(), txt_ans5.getText(), txt_ans6.getText(), Integer.parseInt(txt_correct2.getText())));
                questionDaoImp.editQuestion(new Question(existQuiz.get(1).getQuestion().getId(), existQuiz.get(1).getQuestion().getAnswerId(), txt_q2.getText()));
                //Adding Question3
                answerDaoImp.editAnswer(new Answer(existQuiz.get(2).getQuestion().getAnswerId().getId(), txt_ans7.getText(), txt_ans8.getText(), txt_ans9.getText(), Integer.parseInt(txt_correct3.getText())));
                questionDaoImp.editQuestion(new Question(existQuiz.get(2).getQuestion().getId(), existQuiz.get(2).getQuestion().getAnswerId(), txt_q3.getText()));
                JOptionPane.showMessageDialog(null, "Quiz has been updated!");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/QuizViewAdmin.fxml"));
                Parent root = loader.load();
                Global.getPrimaryStage().getScene().setRoot(root);
                Global.getPrimaryStage().setHeight(getHeight("QuizViewAdmin"));
                Global.getPrimaryStage().setWidth(getWidth("QuizViewAdmin"));
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


}
