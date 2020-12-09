package Beans;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Njaimi Med Aziz
 */

public class OrderItem {
    private int id;
    private Order order;
    private int quantity;
    private Book book;

    public OrderItem(int id, Order orderId, int quantity, Book book) {
        this.id = id;
        this.order = orderId;
        this.quantity = quantity;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book bookId) {
        this.book = bookId;
    }

    // Extra getters used by javafx interfaces

    public String getBookTitle() {
        return book.getTitle();
    }

    public int getBookId() {
        return book.getId();
    }

    public float getBookPrice() {
        return (float) book.getPrice();
    }

    public int getOrderId() {
        return this.order.getId();
    }

    public float getTotalBooksPrice() {
        return (float) (book.getPrice() * quantity);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + order +
                ", quantity=" + quantity +
                ", bookId=" + book.getId() +
                '}';
    }

    public static String afficherTab(ArrayList<OrderItem> tab) {
        String res = "";
        for (int i = 0; i < tab.size(); i++)
            res += "\t" + tab.get(i).toString() + "\n";
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem item = (OrderItem) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
