package Beans;

import java.util.Objects;

public class Comment {
    private int id;
    private String text;
    private Book book;
    private User user;


    public Comment(int id, String text, Book b, User u) {
        this.id = id;
        this.text = text;
        this.book = b;
        this.user = u;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //EXTRA
    public String getUserFullName() {
        return this.user.getFullName();
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", b=" + book +
                ", u=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return getId() == comment.getId() &&
                Objects.equals(getText(), comment.getText()) &&
                Objects.equals(getBook(), comment.getBook()) &&
                Objects.equals(getUser(), comment.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText(), getBook(), getUser());
    }
}
