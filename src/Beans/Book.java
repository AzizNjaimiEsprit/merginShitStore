package Beans;

import java.sql.Date;
import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private double price;
    private String pubHouse;
    private String summary;
    private Date releaseDate;
    private int quantity;
    private String status;
    private Category category;
    private String image ;
    private int nbPage;
    private String Authors;


    public Book(int id, String title, double price, String pubHouse, String summary, Date releaseDate, int quantity, String status, Category category, String image, int nbPage, String authors) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.pubHouse = pubHouse;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.quantity = quantity;
        this.status = status;
        this.category = category;
        this.image = image;
        this.nbPage = nbPage;
        Authors = authors;
    }

    public Book(String title, double price, String pubHouse, String summary, Date releaseDate, int quantity, String status, Category category, String image, int nbPage, String authors) {
        this.title = title;
        this.price = price;
        this.pubHouse = pubHouse;
        this.summary = summary;
        this.releaseDate = releaseDate;
        this.quantity = quantity;
        this.status = status;
        this.category = category;
        this.image = image;
        this.nbPage = nbPage;
        Authors = authors;
    }

    public Book(int id) {
        this.id = id;
    }

    public Book() {

    }
    //*****************Getters***********************

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }


    public double getPrice() {
        return price;
    }

    public String getPubHouse() {
        return pubHouse;
    }


    public String getSummary() {
        return summary;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }

    public int getNbPage() {
        return nbPage;
    }

    public String getAuthors() {
        return Authors; }

    public String getImage() {
        return image;
    }

    //*****************Setters***********************

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void setPrice(double price) {
        this.price = price;
    }

    public void setPubHouse(String pubHouse) {
        this.pubHouse = pubHouse;
    }


    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setNbPage(int nbPage) {
        this.nbPage = nbPage;
    }

    public void setAuthors(String Authors) {
        this.Authors = Authors;
    }

    public void setImage(String images) {
        this.image = image;
    }
    //***********************ToString******************


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", pub_house='" + pubHouse + '\'' +
                ", summary='" + summary + '\'' +
                ", release_date=" + releaseDate +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                ", category_id=" + category.getName() +
                ", authors=" + Authors +
                ", image='" + image + '\'' +
                ", nb_page=" + nbPage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getId() == book.getId() ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getPrice(), getPubHouse(), getSummary(), getReleaseDate(), getQuantity(), getStatus(), getCategory(), getImage(), getNbPage(), getAuthors());
    }
}
