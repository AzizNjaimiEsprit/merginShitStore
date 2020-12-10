package Dao;

import javafx.collections.ObservableList;

public interface QuizDao<T> {
    public void addQuiz(T t);

    public void deleteQuiz(T t);

    public ObservableList<T> getQuizById(int bookId);
}
