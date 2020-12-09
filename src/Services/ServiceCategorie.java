package Services;

import Beans.Category;
import Utility.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceCategorie /*implements IServicesCategory<Category>*/ {
    Connection cnx = Singleton.getConn();

    public void ajouter(Category c) {
        try {

            String req = "INSERT INTO CATEGORY (name) VALUES (?)";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, c.getName());
            st.executeUpdate();
            System.out.println("Category ajout�!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    public void supprimer(Category c) {
        try {

            String req = "DELETE FROM CATEGORY WHERE id=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setInt(1, c.getId());
            st.executeUpdate();
            System.out.println("Category supprim�!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    public Category getCategoryByName(Category category) {
        Category c = null;
        try {
            String req = "SELECT * FROM CATEGORY WHERE name = ?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, category.getName());
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                System.out.println("sercice" + id);
                c = new Category(id, category.getName());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("service cat" + c);
        return c;


    }

    public void modifier(Category c) {
        try {

            String req = "UPDATE CATEGORY SET name=? WHERE id =?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, c.getName());
            st.setInt(2, c.getId());
            st.executeUpdate();
            System.out.println("Category modifi�!");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }


    public ObservableList<Category> afficher() {
        ObservableList<Category> res = FXCollections.observableArrayList();
        try {

            String req = "SELECT * FROM CATEGORY ";
            PreparedStatement st = cnx.prepareStatement(req);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                res.add(new Category(rs.getInt("id"), rs.getString("name")));

            }
            System.out.println("Category r�ccup�r�e!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return res;
    }

}
