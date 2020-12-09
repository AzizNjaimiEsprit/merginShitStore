package Beans;

import java.sql.Date;

public class OnlineBook extends Book {
    private String url ;

        public OnlineBook(int id, String title, double price, String pub_house, String summary, Date release_date, int quantity, String status, Category category_id, String image, int nb_page, String Authors, String url) {
        super(id, title, price, pub_house, summary, release_date, quantity, status, category_id, image, nb_page, Authors);
        this.url = url;
    }

    public OnlineBook( String title, double price, String pub_house, String summary, Date release_date, int quantity, String status, Category category_id, String image, int nb_page, String Authors, String url) {
        super( title, price, pub_house, summary, release_date, quantity, status, category_id, image, nb_page, Authors);
        this.url = url;
    }



    public OnlineBook() {
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", price=" + getPrice() +
                ", pub_house='" + getPubHouse() + '\'' +
                ", summary='" + getSummary() + '\'' +
                ", release_date=" + getReleaseDate() +
                ", quantity=" + getQuantity() +
                ", status='" + getStatus() + '\'' +
                ", category_id=" + getCategory().getName() +
                ", authors=" + getAuthors() +
                ", image='" + getImage() + '\'' +
                ", nb_page=" + getNbPage() +
                "url='" + url + '\'' +
                '}';
    }
}
