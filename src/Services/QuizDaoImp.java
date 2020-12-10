package Services;

import Beans.OnlineBook;
import Beans.Question;
import Beans.Quiz;
import Dao.QuizDao;
import Utility.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class QuizDaoImp implements QuizDao<Quiz> {
    Connection conn = Singleton.getConn();
    CrudOnlineBook crudOnlineBook = new CrudOnlineBook();
    QuestionDaoImp questionDaoImp = new QuestionDaoImp();

    @Override
    public void addQuiz(Quiz quiz) {
        try {
            PreparedStatement st = conn.prepareStatement("INSERT INTO QUIZ VALUES(?,?)");
            st.setInt(1, quiz.getOnlineBookId().getId());
            st.setInt(2, quiz.getQuestion().getId());
            st.executeUpdate();
            System.out.println("Quiz has been added successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteQuiz(Quiz quiz) {
        try {
            PreparedStatement preparedStmt = conn.prepareStatement("DELETE A FROM ANSWER A INNER JOIN " +
                    "QUESTION Q on A.id = Q.answer_id INNER JOIN QUIZ Q2 on Q.id = Q2.question_id INNER JOIN " +
                    "ONLINE_BOOK OB on Q2.book_id = OB.id WHERE Q2.book_id=?");
            preparedStmt.setInt(1, quiz.getOnlineBookId().getId());
            int result = preparedStmt.executeUpdate();
            if (result != 0)
                System.out.println("Quiz has been removed Successfully!");
            else
                System.out.println("Failed to remove quiz, Please try again later!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Quiz> getQuizById(int bookId) {
        ObservableList<Quiz> res = FXCollections.observableArrayList();
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM QUIZ WHERE book_id=?");
            st.setInt(1, bookId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int questionId = rs.getInt("question_id");
                OnlineBook onlineBook = crudOnlineBook.RecupererLivreEnLigneByid(bookId);
                Question question = questionDaoImp.getQuestionById(questionId);
                Quiz newQuiz = new Quiz(onlineBook, question);
                res.add(newQuiz);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

}
