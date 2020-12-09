package Beans;

import java.util.Objects;

public class Answer {
    private int id;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private int correctAnswer;

    public Answer(int id, String firstAnswer, String secondAnswer, String thirdAnswer, int correctAnswer) {
        this.id = id;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.correctAnswer = correctAnswer;
    }

    public Answer(String firstAnswer, String secondAnswer, String thirdAnswer, int correctAnswer) {
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.correctAnswer = correctAnswer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return id == answer.id &&
                correctAnswer == answer.correctAnswer &&
                Objects.equals(firstAnswer, answer.firstAnswer) &&
                Objects.equals(secondAnswer, answer.secondAnswer) &&
                Objects.equals(thirdAnswer, answer.thirdAnswer);
    }


    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", firstAnswer='" + firstAnswer + '\'' +
                ", secondAnswer='" + secondAnswer + '\'' +
                ", thirdAnswer='" + thirdAnswer + '\'' +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
