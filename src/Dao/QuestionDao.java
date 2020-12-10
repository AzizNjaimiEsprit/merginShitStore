package Dao;

import java.util.ArrayList;

public interface QuestionDao<T> {
    public T addQuestion(T t);

    public void editQuestion(T t);

    public ArrayList<T> displayQuestions();

    public T getQuestionById(T t);

    public T getQuestionById(int t);
}
