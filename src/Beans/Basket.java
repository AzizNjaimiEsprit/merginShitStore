package Beans;

public class Basket {
    private User user;
    private Book book;
    private int quantity;


    public Basket(User user, Book book, int quantity) {

        this.user = user;
        this.book = book;
        this.quantity = quantity;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Basket other = (Basket) obj;
        if (book == null) {
            if (other.book != null)
                return false;
        } else if (!book.equals(other.book))
            return false;
        if (quantity != other.quantity)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Basket [user=" + user + ", book=" + book + ", quantity=" + quantity + "]";
    }

    public String getBookTitle() {
        return book.getTitle();
    }

    public double getBookPrice() {
        return book.getPrice();
    }

    public double getTotalPrice() {
        return this.getQuantity() * this.getBookPrice();
    }


}
