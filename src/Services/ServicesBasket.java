package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Beans.Basket;
import Beans.Book;
import Beans.User;
import Utility.Global;
import Utility.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServicesBasket /*implements IServiceBasket<Basket>*/ {
    Connection cnx = Singleton.getConn();
    CrudBook cb = new CrudBook();

    public void ajouter(Basket b) {
        try {

            String req = "INSERT INTO BASKET VALUES (? , ? , ?)";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, b.getUser().getId());
            st.setInt(2, b.getBook().getId());
            st.setInt(3, b.getQuantity());
            st.executeUpdate();
            System.out.println("Basket ajout�!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    public void supprimer(Basket b) {
        try {

            String req = "DELETE FROM BASKET WHERE user_id=? AND book_id=? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, b.getUser().getId());
            st.setInt(2, b.getBook().getId());
            st.executeUpdate();
            System.out.println("Basket supprim�!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    public void modifier(Basket b) {
        System.out.println(b);
        try {

            String req = "UPDATE BASKET SET quantity=? WHERE user_id=? AND book_id=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(2, b.getUser().getId());
            st.setInt(3, b.getBook().getId());
            st.setInt(1, b.getQuantity());
            st.executeUpdate();
            System.out.println("Panier modifi�e!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

   /* public List<Basket> afficher() {
        List<Basket> List = new ArrayList<>();
        try {

            String req = "SELECT * FROM BASKET ";
            PreparedStatement st = cnx.prepareStatement(req);
            ResultSet res = st.executeQuery();
            while (res.next()) {
                List.add(new Basket(new User(res.getInt("user_id")), new Book(res.getInt("book_id")), res.getInt("quantity")));

            }
            System.out.println("Basket r�ccup�r�e!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return List;

    }*/


            public ObservableList<Basket> getBasketOfUser(int userId){
                ObservableList<Basket> res= FXCollections.observableArrayList();
                try{

                    String req = "Select * FROM BASKET WHERE user_id=? ";
                    PreparedStatement st = cnx.prepareStatement(req);
                    st.setInt(1, userId);
                    ResultSet rs = st.executeQuery();
                    while (rs.next()){
                        int quantity = rs.getInt("quantity");
                        int bookid = rs.getInt("book_id");
                        Book book = cb.RecupererLivreByID(bookid);
                        User user= Global.getCurrentUser();
                        res.add(new Basket(user,book,quantity));
                    }
                    rs.close();
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                return res;

            }



        }




	
	
	

