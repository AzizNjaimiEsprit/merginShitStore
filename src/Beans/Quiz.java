package Beans;

import javafx.scene.control.Button;

import java.util.Objects;

public class Quiz {
    private User user;
    private OnlineBook onlineBookId;
    private Question question;
    private Button addButton;
    private Button updateButton;
    private Button removeButton;

    public Quiz(User user, OnlineBook onlineBookId, Question question) {
        this.user = user;
        this.onlineBookId = onlineBookId;
        this.question = question;
    }

    public Quiz(OnlineBook onlineBookId, Question question) {
        this.onlineBookId = onlineBookId;
        this.question = question;
    }

    public Quiz(OnlineBook onlineBookId, Button addButton, Button updateButton, Button removeButton) {
        this.onlineBookId = onlineBookId;
        this.addButton = addButton;
        this.addButton.setText("Add quiz");
        this.updateButton = updateButton;
        this.updateButton.setText("Update quiz");
        this.removeButton = removeButton;
        this.removeButton.setText("Remove quiz");
    }

    public OnlineBook getOnlineBookId() {
        return onlineBookId;
    }

    public void setOnlineBookId(OnlineBook onlineBookId) {
        onlineBookId = onlineBookId;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Button getAddButton() {
        return addButton;
    }

    public void setAddButton(Button addButton) {
        this.addButton = addButton;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(Button updateButton) {
        this.updateButton = updateButton;
    }

    public Button getRemoveButton() {
        return removeButton;
    }

    public void setRemoveButton(Button removeButton) {
        this.removeButton = removeButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quiz quiz = (Quiz) o;
        return
                Objects.equals(user, quiz.user) &&
                        Objects.equals(onlineBookId, quiz.onlineBookId) &&
                        Objects.equals(question, quiz.question);
    }


    @Override
    public String toString() {
        return "Quiz{" +
                "OnlineBookId=" + onlineBookId +
                ", question=" + question +
                '}';
    }

    public String getBookTitle() {
        return onlineBookId.getTitle();
    }
}
