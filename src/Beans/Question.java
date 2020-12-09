package Beans;

public class Question {
    private int id;
    private Answer answerId;
    private String question;

    public Question(int id, Answer answerId, String question) {
        this.id = id;
        this.answerId = answerId;
        this.question = question;
    }

    public Question(Answer answerId, String question) {
        this.answerId = answerId;
        this.question = question;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Answer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Answer answerId) {
        this.answerId = answerId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", answerId=" + answerId +
                ", question='" + question + '\'' +
                '}';
    }
}
