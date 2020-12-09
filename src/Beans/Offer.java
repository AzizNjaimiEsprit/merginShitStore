package Beans;

import Services.OfferService;
import javafx.scene.control.Button;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Offer {
    private int id;
    private User user;
    private String title;
    private String description;
    private String author;
    private float price;
    private String image;
    private String bought;
    private Timestamp submitDate;
    private Button acceptOffer;
    private Button declineOffer;

    // Get data from database
    public Offer(int id, User user, String title, String description, String author, float price, String image, String bought, Timestamp submitDate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.image = image;
        this.bought = bought;
        this.submitDate = submitDate;
    }

    //Insert data into database
    public Offer(User user, String title, String description, String author, float price, String image) {
        this.user = user;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.image = image;
    }

    // Insert data into TableView JavaFX
    public Offer (int id, User user, String title,String description, String author, float price, String image, String bought,Timestamp submitDate, Button acceptOffer, Button declineOffer) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.description = description;
        this.author = author;
        this.price = price;
        this.image = image;
        this.bought = bought;
        this.submitDate = submitDate;
        this.acceptOffer = acceptOffer;
        this.declineOffer = declineOffer;
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

    public Button getAcceptOffer() {
        return acceptOffer;
    }

    public Button getDeclineOffer() {
        return declineOffer;
    }

    public String getSellerName () {
        OfferService offerService = new OfferService();
        return offerService.getSeller(this.getUser().getId());
    }

    public String getBought () {return this.bought;}

    public void setBought (String bought) {this.bought = bought;}

    public Timestamp getSubmitDate () {return this.submitDate;}



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
