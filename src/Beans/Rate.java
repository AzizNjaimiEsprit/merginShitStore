package Beans;

import java.util.Objects;

public class Rate {
    private int id ;
    private int rate ;
    private Book book;
    private User user;

    public Rate(int id, Book book, User user ,int rate) {
        this.id = id;
        this.book = book;
        this.user = user;
        this.rate = rate;
    }

    public Rate( ) {

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Book getB() {
        return book;
    }

    public void setB(Book b) {
        this.book = b;
    }

    public User getU() {
        return user;
    }

    public void setU(User u) {
        this.user = u;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "id=" + id +
                ", rate=" + rate +
                ", b=" + book +
                ", u=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate1 = (Rate) o;
        return getId() == rate1.getId();
    }


    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRate(), book, user);
    }
}
