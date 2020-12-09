package Beans;

import javafx.scene.control.Button;

import java.util.Objects;

public class Library {
    private User user;
    private OnlineBook book;
    private String status;
    private int reachedPage;
    private int quizScore;
    private Button resumeButton;
    private Button readButton;
    private Button quizButton;

    public Library(){

    }

    public Library(User user, OnlineBook book, String status, int reachedPage,int quizScore) {
        this.user = user;
        this.book = book;
        this.status = status;
        this.reachedPage = reachedPage;
        this.quizScore=quizScore;
    }

    public Library(OnlineBook book, String status, int reachedPage,int quizScore) {
        this.book = book;
        this.status = status;
        this.reachedPage = reachedPage;
        this.quizScore=quizScore;
    }

    public Library(OnlineBook book, String status, int reachedPage,Button resumeButton, Button readButton, Button quizButton) {
        this.book = book;
        this.status = status;
        this.reachedPage = reachedPage;
        this.resumeButton = resumeButton;
        this.resumeButton.setText("Listen resume");
        this.readButton = readButton;
        this.readButton.setText("Start reading");
        this.quizButton = quizButton;
        this.quizButton.setText("Start quiz");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public OnlineBook getBook() {
        return book;
    }

    public void setBook(OnlineBook book) {
        this.book = book;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public int getReachedPage() {
        return reachedPage;
    }

    public void setReachedPage(int reachedPage) {
        this.reachedPage = reachedPage;
    }

    public int getQuizScore() {
        return quizScore;
    }

    public void setQuizScore(int quizScore) {
        this.quizScore = quizScore;
    }

    public Button getResumeButton() {
        return resumeButton;
    }

    public void setResumeButton(Button resumeButton) {
        this.resumeButton = resumeButton;
    }

    public Button getReadButton() {
        return readButton;
    }

    public void setReadButton(Button readButton) {
        this.readButton = readButton;
    }

    public Button getQuizButton() {
        return quizButton;
    }

    public void setQuizButton(Button quizButton) {
        this.quizButton = quizButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return Objects.equals(user, library.user) &&
                Objects.equals(book, library.book);
    }

    @Override
    public String toString() {
        return "Library{" +
                ", user=" + user +
                ", book=" + book +
                ", status='" + status + '\'' +
                ", reachedPage=" + reachedPage +
                ", quizScore=" + quizScore +
                '}';
    }

    public String getBookTitle(){
        return book.getTitle();
    }
}
