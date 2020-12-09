package Beans;


import javafx.scene.control.Button;

public class Chat {
    private int id;
    private String question;
    private String reponse;
    private Button updateButton;

    public Chat(int id, String question, String reponse) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
    }

    public Chat(String question, String reponse) {
        this.question = question;
        this.reponse = reponse;
    }

    public Chat(String question, String reponse, Button updateButton) {
        this.question = question;
        this.reponse = reponse;
        this.updateButton = updateButton;
        this.updateButton.setText("Update Row");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Button getUpdateButton() {
        return updateButton;
    }

    public void setUpdateButton(Button updateButton) {
        this.updateButton = updateButton;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id == chat.id;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", reponse='" + reponse + '\'' +
                '}';
    }
}
