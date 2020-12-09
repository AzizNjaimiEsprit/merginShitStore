package Services;

import Beans.Answer;
import Beans.Question;
import Utility.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QuestionDaoImp implements QuestionDao<Question> {
    Connection conn = Singleton.getConn();
    AnswerDaoImp answerDaoImp = new AnswerDaoImp();

    @Override
    public Question addQuestion(Question question) {
        Question newQuestion = null;
        try {
            for (Question i : displayQuestions()) {
                if (i.equals(question)) {
                    System.out.println("Question already exist!");
                    return null;
                }
            }
            PreparedStatement st = conn.prepareStatement("INSERT INTO QUESTION (answer_id, question) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setInt(1, question.getAnswerId().getId());
            st.setString(2, question.getQuestion());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                String id = rs.getString(1);
                newQuestion = getQuestionById(Integer.parseInt(id));
            }
            System.out.println("Question has been added successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newQuestion;
    }

    @Override
    public void editQuestion(Question question) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE QUESTION SET question=? WHERE id=?");
            st.setString(1, question.getQuestion());
            st.setInt(2, question.getId());
            st.executeUpdate();
            System.out.println("Question has been updated successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Question> displayQuestions() {
        ArrayList<Question> res = new ArrayList<>();
        try {
            PreparedStatement preparedStat = conn.prepareStatement("SELECT * FROM QUESTION");
            ResultSet rs = preparedStat.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                int answerId = rs.getInt("answer_id");
                Answer answer = answerDaoImp.getAnswerById(answerId);
                res.add(new Question(id, answer, question));
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public Question getQuestionById(Question question) {
        Question res = null;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM QUESTION WHERE id=?");
            st.setInt(1, question.getId());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("question");
                int answerId = rs.getInt("answer_id");
                Answer answer = answerDaoImp.getAnswerById(answerId);
                res = new Question(id, answer, questionText);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public Question getQuestionById(int questioId) {
        Question res = null;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM QUESTION WHERE id=?");
            st.setInt(1, questioId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String questionText = rs.getString("question");
                int answerId = rs.getInt("answer_id");
                Answer answer = answerDaoImp.getAnswerById(answerId);
                res = new Question(id, answer, questionText);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
