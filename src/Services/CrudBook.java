package Services;

import Beans.Book;
import Beans.Category;
import Utility.Singleton;
import api.TechnicalSheetCreation;

import java.sql.*;
import java.util.ArrayList;

public class CrudBook {
    Connection cnn = Singleton.getConn();

    //*********************Ajout livre *****************************
    public int AjouterLivre(Book b) {
        TechnicalSheetCreation t = new TechnicalSheetCreation();
        if (!(b.equals(RecupererLivre(b)))) {
            System.out.println("i'm here");
            try {
                PreparedStatement preparedStat = cnn.prepareStatement(" INSERT INTO BOOK VALUES (NULL ,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                preparedStat.setString(1, b.getTitle());
                preparedStat.setDouble(2, b.getPrice());
                preparedStat.setString(3, b.getPubHouse());
                preparedStat.setString(4, b.getSummary());
                preparedStat.setDate(5, b.getReleaseDate());
                preparedStat.setInt(6, b.getQuantity());
                preparedStat.setString(7, b.getStatus());
                preparedStat.setInt(8, b.getCategory().getId());
                preparedStat.setString(9, b.getImage());
                preparedStat.setInt(10, b.getNbPage());
                preparedStat.setString(11, b.getAuthors());

                //executing the request
                preparedStat.executeUpdate();
                System.out.println("Le livre est ajouter");
                //t.CreatTS(b);

                ResultSet rs = preparedStat.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("le livre existe deja ");
        }
        return -9;
    }

//*********************Suppression livre *****************************

    public void SupprimerLivre(Book b) {
        if (b.equals(RecupererLivre(b))) {
            try {
                PreparedStatement preparedStat = cnn.prepareStatement(" DELETE FROM BOOK WHERE id=" + b.getId());
                //executing the request
                preparedStat.executeUpdate();
                System.out.println("Le livre " + b.getId() + "est supprimer avec succée");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("le livre n'existe pas ");
        }
    }


//*********************modification livre *****************************

    public void ModifierLivre(Book b) {

        try {
            PreparedStatement preparedStat = cnn.prepareStatement(" UPDATE BOOK b SET title=? , price =? ,pub_house=?,summary=?," +
                    "release_date=?,quantity=?,status =?,category_id=?," + "image=?,nb_page=?,author=? WHERE b.id=" + b.getId());
            preparedStat.setString(1, b.getTitle());
            preparedStat.setDouble(2, b.getPrice());
            preparedStat.setString(3, b.getPubHouse());
            preparedStat.setString(4, b.getSummary());
            preparedStat.setDate(5, b.getReleaseDate());
            preparedStat.setInt(6, b.getQuantity());
            preparedStat.setString(7, b.getStatus());
            preparedStat.setInt(8, b.getCategory().getId());
            preparedStat.setString(9, b.getImage());
            preparedStat.setInt(10, b.getNbPage());
            preparedStat.setString(11, b.getAuthors());

            //executing the request
            preparedStat.executeUpdate();
            System.out.println("livre " + b.getId() + "est modifier ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int RecupererQuantitéLivre(Book b) {
        if (b.equals(RecupererLivre(b))) {
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
        return Integer.parseInt(null);
    }


    public void ModifierQuantitéLivre(Book b) {
        if (b.equals(RecupererLivre(b))) {
            try {
                PreparedStatement preparedStat = cnn.prepareStatement(" UPDATE BOOK SET quantity='" + b.getQuantity() + "' WHERE id=" + b.getId());
                //executing the request
                preparedStat.executeUpdate();
                System.out.println("La quantité du livre " + b.getId() + "est modifier ");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            System.out.println("le livre n'existe pas ");
        }
    }

    //*********************************Recuperation*******************************************
    public ArrayList<Book> RecupererListLivre() {
        ArrayList<Book> lLivre = new ArrayList<Book>();
        try {
            String req = "SELECT * from BOOK join CATEGORY C on BOOK.category_id = C.id  ";
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            //*********************************
            while (res.next()) {
                lLivre.add(new Book(res.getInt("id"),
                        res.getString("title"),
                        res.getFloat("price"),
                        res.getString("pub_house"),
                        res.getString("summary"),
                        res.getDate("release_date"),
                        res.getInt("quantity"),
                        res.getString("status"),
                        new Category(res.getInt("category_id"), res.getString("name")),
                        res.getString("image"),
                        res.getInt("nb_page"),
                        res.getString("author")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lLivre;
    }

    public Book RecupererLivre(Book b) {
        Book livre = new Book();

        try {

            String req = "SELECT * from BOOK b join CATEGORY C on b.category_id = C.id WHERE b.id=" + b.getId();
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Book bk = new Book(b.getId(),
                        res.getString("title"),
                        res.getFloat("price"),
                        res.getString("pub_house"),
                        res.getString("summary"),
                        res.getDate("release_date"),
                        res.getInt("quantity"),
                        res.getString("status"),
                        new Category(res.getInt("category_id"), res.getString("name")),
                        res.getString("image"),
                        res.getInt("nb_page"),
                        res.getString("author"));
                livre = bk;
            }
            System.out.println("la livre est recuperer   ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return livre;
    }

    public Book RecupererLivreByID(int id) {
        Book livre = new Book();

        try {

            String req = "SELECT * from BOOK b join CATEGORY C on b.category_id = C.id WHERE b.id=" + id;
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                Book bk = new Book(id,
                        res.getString("title"),
                        res.getFloat("price"),
                        res.getString("pub_house"),
                        res.getString("summary"),
                        res.getDate("release_date"),
                        res.getInt("quantity"),
                        res.getString("status"),
                        new Category(res.getInt("category_id"), res.getString("name")),
                        res.getString("image"),
                        res.getInt("nb_page"),
                        res.getString("author"));
                livre = bk;
            }
            System.out.println("la livre est recuperer   ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return livre;
    }
}
