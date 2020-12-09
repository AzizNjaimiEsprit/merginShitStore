package Services;

import Beans.Book;
import Beans.Comment;
import Beans.User;
import Utility.Singleton;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CrudComment {
    Connection cnn = Singleton.getConn();

    public void AjouterCommentaire(Comment c) {
        try {
            PreparedStatement ps = cnn.prepareStatement("INSERT INTO COMMENT VALUES (?,?,?,?)");
            ps.setInt(1, c.getId());
            ps.setInt(2, c.getBook().getId());
            ps.setInt(3, c.getUser().getId());
            ps.setString(4, c.getText());
            ps.executeUpdate();
            System.out.println("le commantaire est ajouter ");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void SupprimerComment(Comment c) {
        try {
            PreparedStatement ps = cnn.prepareStatement("DELETE FROM COMMENT WHERE id=?");
            ps.setInt(1, c.getId());
            ps.executeUpdate();
            System.out.println("commentaire  est supprimer ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void ModifierComment(Comment c) {
        try {
            PreparedStatement ps = cnn.prepareStatement("UPDATE COMMENT SET text='" + c.getText() + "' WHERE id=" + c.getId());
            ps.executeUpdate();
            System.out.println("commentaire est modifier  ");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Comment> RecupererListComment(Book b) {
        List<Comment> lcomment = new ArrayList<Comment>();
        try {

            String req = "SELECT c.* , full_name from COMMENT c join USER U on U.id = c.user_id WHERE id_book=" + b.getId();
            PreparedStatement ps = cnn.prepareStatement(req);
            ResultSet res = ps.executeQuery();
            while (res.next()) {
                User u = new User(res.getInt("user_id"));
                u.setFullName(res.getString("full_name"));
                lcomment.add(new Comment(
                        res.getInt("id"),
                        res.getString("text"),
                        new Book(res.getInt("id_book")),
                        u));
            }
            System.out.println("le commentaire est recuperer");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return lcomment;
    }
}
