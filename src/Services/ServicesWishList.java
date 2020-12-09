package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Beans.*;

import Utility.Global;
import Utility.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ServicesWishList /*implements IServiceWishList <WishList>*/ {
    Connection cnx = Singleton.getConn();
    CrudBook cb = new CrudBook();

    public void ajouter(WishList wl) {
        try {
            String req = "INSERT INTO WISHLIST VALUES (? , ?)";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, wl.getUser().getId());
            st.setInt(2, wl.getBook().getId());
            st.executeUpdate();
            System.out.println("WishList ajout�!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    public void supprimer(WishList wl) {
        try {

            String req = "DELETE FROM WISHLIST WHERE user_id=? AND book_id=? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, wl.getUser().getId());
            st.setInt(2, wl.getBook().getId());
            st.executeUpdate();
            System.out.println("WishList supprim�!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public ObservableList<WishList> getWishListOfUser(int userId) {
        ObservableList<WishList> res = FXCollections.observableArrayList();
        try {
            String req = "Select * FROM WISHLIST WHERE user_id=? ";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int bookid = rs.getInt("book_id");
                Book book = cb.RecupererLivreByID(bookid);
                User user = Global.getCurrentUser();
                res.add(new WishList(user, book));
            }
            rs.close();
            return res;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

}
