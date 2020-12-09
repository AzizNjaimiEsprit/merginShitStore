package Services;
import Services.IServiceClient;
import Services.IServiceUser;
import Beans.Client;
import Beans.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.mindrot.jbcrypt.BCrypt;
import Utility.Singleton;

import java.sql.*;
import java.util.ArrayList;

public class clientService implements IServiceClient<Client> {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    public clientService() {
        cnx = Singleton.getConn();
    }

    @Override
    public void AddClient(Client t) {
       // userService us= new userService();
        //us.AddAdmin(t);
        ObservableList <Client> allusers=GetAllClient();
        for (Client u : allusers ) {
            System.out.println(u);
            if (u.getEmail().equals(t.getEmail())) {
                System.out.println("Email already used");
                afficherAlert("Email already used", Alert.AlertType.ERROR);
                return;
            }
            if (u.getLogin().equals(t.getLogin())) {
                System.out.println("Login already used");
                afficherAlert("Login already used", Alert.AlertType.ERROR);
                return;
            }
        }

        String req = "Insert into CLIENT (zip_code,adresse,id_user)  values (?,?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setInt(1, t.getZipCode());
            pst.setString(2, t.getAddress());
            pst.setInt(3, t.getId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Account Client created successfully!");
        //afficherAlert("Account Client created successfully!", Alert.AlertType.INFORMATION);
    }

    @Override
    public void UpdateClient(String adresse,int zip_code, int id) {
        String req = "update CLIENT set adresse = ?,zip_code = ? where id = ? ";
        try {
            pst = cnx.prepareStatement(req);

            pst.setString(1,adresse);
            pst.setInt(2,zip_code);
            pst.setInt(3,id);
            pst.executeUpdate();
            System.out.println("Client: successfully updated");
           // afficherAlert("Client: successfully updated", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void DeleteClient(int id) {
        String req = "delete from CLIENT where id ="+id;
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);
            System.out.println("Account Client successfully deleted");
            //afficherAlert("Account Client successfully deleted", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void afficherAlert(String message, Alert.AlertType t) {
        Alert alert = new Alert(t);
        alert.setTitle("Information Dialog");
        //alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
    @Override
    public ObservableList<Client> GetAllClient() {
        String req = "SELECT c.*,u.* FROM CLIENT as c inner join USER as u on c.id_user=u.id ";
        ObservableList<Client> list= FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                list.add(new Client (rs.getInt("u.id"),rs.getString("u.full_name"),rs.getString("u.email"),rs.getString("u.telephone"),rs.getString("u.login"),rs.getString("u.password"),rs.getInt("c.id"),rs.getString("c.adresse"),rs.getInt("c.zip_code")));
                //System.out.println(list);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }


    @Override
    public Client GetClient(int id) {
        Client u = new Client();
        try {
            String req = "SELECT c.*,u.* FROM CLIENT as c inner join USER as u on c.id_user=u.id where u.id='" + id + "' ";
            pst = cnx.prepareStatement(req);
            rs = pst.executeQuery(req);
            while (rs.next()) {
                u = new Client (rs.getInt("u.id"),rs.getString("u.full_name"),rs.getString("u.email"),rs.getString("u.telephone"),rs.getString("u.login"),rs.getString("u.password"),rs.getInt("c.id"),rs.getString("c.adresse"),rs.getInt("c.zip_code"));
            }
        }catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
}
