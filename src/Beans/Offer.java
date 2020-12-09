package Beans;

import java.util.Objects;

public class Offer {
    private int id;
    private User user;
    private String title;
    private String description;
    private String author;
    private float price;
    private String image;

    public Offer(int id, User user, String title, String description, String author, float price, String image) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.image = image;
    }

    // Getters and Setters
    public int getId () {return this.id;}

    public User getUser () {return this.user;}

    public void setUser (User user) {this.user = user;}

    public String getTitle () {return this.title;}

    public void setTitle (String title) {this.title = title;}

    public String getDescription () {return this.description;}

    public void setDescription (String description) {this.description = description;}

    public String getAuthor () {return this.author;}

    public void setAuthor (String author) {this.author = author;}

    public float getPrice () {return this.price;}

    public void setPrice (float price) {this.price = price;}

    public String getImage () {return this.image;}

    public void setImage (String image) {this.image = image;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id == offer.id &&
                Float.compare(price, offer.getPrice()) == 0 &&
                Objects.equals(user, offer.getUser()) &&
                Objects.equals(title, offer.getTitle()) &&
                Objects.equals(description, offer.getDescription());
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
