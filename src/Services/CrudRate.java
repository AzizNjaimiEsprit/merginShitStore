package Services;

import Beans.Book;
import Beans.OnlineBook;
import Beans.Rate;
import Beans.User;
import Utility.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrudRate {
    Connection cnn = Singleton.getConn();


    public void AjouterRate(Rate r) {
        System.out.println(RecupererRate(r));
        if (!r.equals(RecupererRate(r))) {
            try {
                PreparedStatement ps = cnn.prepareStatement("INSERT INTO RATE VALUES (?,?,?,?)");
                ps.setInt(1, r.getId());
                ps.setInt(2, r.getB().getId());
                ps.setInt(3, r.getU().getId());
                ps.setDouble(4, r.getRate());
                ps.executeUpdate();
                System.out.println("l'evaluation est ajouter ");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("Vous avez deja donner votre avis ");

        }
    }

    public void SupprimerRate(Rate r) {
        try {
            PreparedStatement ps = cnn.prepareStatement("DELETE FROM RATE WHERE id=?");
            ps.setInt(1, r.getId());
            ps.executeUpdate();
            System.out.println("evaluation  est supprimer ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ModifierRate(Rate r) {
        try {
            PreparedStatement ps = cnn.prepareStatement("UPDATE RATE SET rate='" + r.getRate() + "' WHERE id=" + r.getId());
            ps.executeUpdate();
            System.out.println("Evaluation est modifier  ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Rate RecupererRate(Rate r) {
        try {
            String req = "SELECT r.* , full_name from RATE r join USER U on U.id = r.user_id WHERE r.id_book=" + r.getB().getId() + " AND r.user_id=" + r.getU().getId();
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            System.out.println("cc1");
            while (res.next()) {
                System.out.println("cc2");
                User u = new User(res.getInt("user_id"));
                u.setFullName(res.getString("full_name"));
                Rate rate = new Rate(
                        res.getInt("id"),
                        new Book(res.getInt("id_book")),
                        u,
                        res.getInt("rate"));
                System.out.println("rate est recuperer ");
                return rate;

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public double getMoyRates(Book b) {
        try {
            String req = "SELECT AVG(rate) FROM RATE  WHERE id_book= " + b.getId();
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                return res.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
}
