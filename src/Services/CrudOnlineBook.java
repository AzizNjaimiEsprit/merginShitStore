package Services;

import Beans.Category;
import Beans.OnlineBook;
import Utility.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CrudOnlineBook {
    Connection cnn = Singleton.getConn();
    CrudBook cb = new CrudBook();

    //*********************Ajout livre en ligne *****************************
    public int AjouterLivreEnLigne(OnlineBook o) {
        int x = cb.AjouterLivre(o);
        System.out.println("inserted id =" + x);
        if (x == -9) return -9;
        try {

            PreparedStatement ps = cnn.prepareStatement("INSERT INTO ONLINE_BOOK VALUES (?,?)");
            ps.setInt(1, x);
            ps.setString(2, o.getUrl());
            ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return x;
    }

    //*********************Suppression livre en ligne *****************************

    public void SupprimerLivreEnLigne(OnlineBook o) {
        cb.SupprimerLivre(o);
        try {
            PreparedStatement ps = cnn.prepareStatement("DELETE FROM ONLINE_BOOK WHERE id=" + o.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //*********************Modification livre en ligne *****************************
    public void ModifierLivreEnLigne(OnlineBook o) {
        cb.ModifierLivre(o);
        try {
            PreparedStatement ps = cnn.prepareStatement("UPDATE ONLINE_BOOK ob SET url=? WHERE id=" + o.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //*********************Recuperation livre en ligne *****************************
    public OnlineBook RecupererLivreEnLigne(OnlineBook o) {
        OnlineBook b = (OnlineBook) cb.RecupererLivre(o);

        try {
            String req = "SELECT url from ONLINE_BOOK join BOOK b on ONLINE_BOOK.id = b.id WHERE b.id=" + o.getId();
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            System.out.println("done");
            if (res.next())
              /*  OB = new OnlineBook(b.getId(), b.getTitle(),b.getPrice(),b.getPubHouse(),b.getSummary(),b.getReleaseDate(),
                        b.getQuantity(),b.getStatus(),b.getCategory(),b.getImage(),b.getNbPage(),b.getAuthors(), res.getString("url"));*/ {
                b.setUrl(res.getString("url"));
                return b;
            } else System.out.println("online book not found");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public OnlineBook RecupererLivreEnLigneByid(int id) {
        OnlineBook OB = null;

        try {
            String req = "SELECT c.* , b.*, url from ONLINE_BOOK join BOOK b on ONLINE_BOOK.id = b.id join CATEGORY c on b.category_id = c.id WHERE b.id=" + id;
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                OB = new OnlineBook(res.getInt("b.id"),
                        res.getString("title"),
                        res.getFloat("price"),
                        res.getString("pub_house"),
                        res.getString("summary"),
                        res.getDate("release_date"),
                        res.getInt("quantity"),
                        res.getString("status"),
                        new Category(res.getInt("c.id"), res.getString("name")),
                        res.getString("image"),
                        res.getInt("nb_page"),
                        res.getString("author"),
                        res.getString("url"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return OB;
    }


    public ArrayList<OnlineBook> RecupererListLivreEnLigne() {
        ArrayList<OnlineBook> LOlivre = new ArrayList<OnlineBook>();

        try {
            String req = "SELECT c.* , b.*, url from ONLINE_BOOK join BOOK b on ONLINE_BOOK.id = b.id join CATEGORY c on b.category_id = c.id ;";
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();

            while (res.next()) {
                LOlivre.add(new OnlineBook(res.getInt("b.id"),
                        res.getString("title"),
                        res.getFloat("price"),
                        res.getString("pub_house"),
                        res.getString("summary"),
                        res.getDate("release_date"),
                        res.getInt("quantity"),
                        res.getString("status"),
                        new Category(res.getInt("c.id"), res.getString("name")),
                        res.getString("image"),
                        res.getInt("nb_page"),
                        res.getString("author"),
                        res.getString("url")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return LOlivre;
    }

    public int RecupererQuantitéLivre(OnlineBook b) {
        if (b.equals(RecupererLivreEnLigne(b))) {
            try {
                PreparedStatement preparedStat = cnn.prepareStatement(" SELECT quantity From BOOK  WHERE id=" + b.getId());
                ResultSet res = preparedStat.executeQuery();
                while (res.next())
                    return res.getInt("quantity");
                System.out.println("La quantité du livre " + b.getId() + "est modifier ");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("le livre n'existe pas ");
        }
        return -1;
    }
}
