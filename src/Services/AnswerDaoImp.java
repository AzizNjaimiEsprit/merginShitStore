package Services;

import Beans.Answer;
import Utility.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class AnswerDaoImp implements AnswerDao<Answer> {
    Connection conn = Singleton.getConn();

    @Override
    public Answer addAnswer(Answer answer) {
        Answer newAnswer = null;
        try {
            for (Answer i : displayAnswers()) {
                if (i.equals(answer)) {
                    System.out.println("Answer already exist!");
                    return null;
                }
            }
            PreparedStatement st = conn.prepareStatement("INSERT INTO ANSWER (first_answer, second_answer, third_answer, correct_answer) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, answer.getFirstAnswer());
            st.setString(2, answer.getSecondAnswer());
            st.setString(3, answer.getThirdAnswer());
            st.setInt(4, answer.getCorrectAnswer());
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                String id = rs.getString(1);
                newAnswer = getAnswerById(Integer.parseInt(id));
            }
            System.out.println("Answer has been added successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return newAnswer;
    }

    @Override
    public void editAnswer(Answer answer) {
        try {
            PreparedStatement st = conn.prepareStatement("UPDATE ANSWER SET first_answer=? ,second_answer=?,third_answer=?,correct_answer=? WHERE id=?");
            st.setString(1, answer.getFirstAnswer());
            st.setString(2, answer.getSecondAnswer());
            st.setString(3, answer.getThirdAnswer());
            st.setInt(4, answer.getCorrectAnswer());
            st.setInt(5, answer.getId());
            st.executeUpdate();
            System.out.println("Answer has been updated successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<Answer> displayAnswers() {
        ArrayList<Answer> res = new ArrayList<>();
        try {
            PreparedStatement preparedStat = conn.prepareStatement("SELECT * FROM ANSWER");
            ResultSet rs = preparedStat.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstAnswer = rs.getString("first_answer");
                String secondAnswer = rs.getString("second_answer");
                String thirdAnswer = rs.getString("third_answer");
                int correctAnswer = rs.getInt("correct_answer");
                Answer answer = new Answer(id, firstAnswer, secondAnswer, thirdAnswer, correctAnswer);
                res.add(answer);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public Answer getAnswerItem(Answer answer) {
        Answer res = null;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM ANSWER WHERE id=?");
            st.setString(1, answer.getFirstAnswer());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstAnswer = rs.getString("first_answer");
                String secondAnswer = rs.getString("second_answer");
                String thirdAnswer = rs.getString("third_answer");
                int correctAnswer = rs.getInt("correct_answer");
                res = new Answer(id, firstAnswer, secondAnswer, thirdAnswer, correctAnswer);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public Answer getAnswerById(int answerId) {
        Answer res = null;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM ANSWER WHERE id=?");
            st.setInt(1, answerId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstAnswer = rs.getString("first_answer");
                String secondAnswer = rs.getString("second_answer");
                String thirdAnswer = rs.getString("third_answer");
                int correctAnswer = rs.getInt("correct_answer");
                res = new Answer(id, firstAnswer, secondAnswer, thirdAnswer, correctAnswer);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
