package Dao;

import java.util.ArrayList;

public interface AnswerDao<T> {
    public T addAnswer(T t);

    public void editAnswer(T t);

    public ArrayList<T> displayAnswers();

    public T getAnswerById(int t);

    public T getAnswerItem(T t);
}
