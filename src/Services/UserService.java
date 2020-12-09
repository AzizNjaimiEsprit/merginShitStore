package Services;

import Beans.Client;
import Beans.User;
import api.MailingService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.mindrot.jbcrypt.BCrypt;
import Utility.Singleton;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;

import java.sql.*;
import java.util.*;

public class UserService implements IServiceUser<User> {
    private Connection cnx;
    private Statement ste;
    private PreparedStatement pst;
    private ResultSet rs;
    private int temprestid;
    public static int coderest = 0;
    public static int idverif = 0;
    private final static String ACCOUNT_SID = "AC4bde583360226b8f1ede5b56f62ebe27";
    private final static String AUTH_TOKEN = "7e16edda188e9333a444c8f289c93669";
    MailingService mailingService = new MailingService();

    public UserService() {
        cnx = Singleton.getConn();
    }

    @Override
    public void AddAdmin(User t) {
        ObservableList<User> allusers = GetAllUser();
        for (User u : allusers) {
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
        String req = "Insert into USER values (NULL,?,?,?,?,?,0)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, t.getFullName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getTelephone());
            pst.setString(4, t.getLogin());
            pst.setString(5, BCrypt.hashpw(t.getPassword(), BCrypt.gensalt(12)));
            String verifcode = BCrypt.hashpw(t.getFullName(), BCrypt.gensalt(12));
            UserService us = new UserService();
            mailingService.sendConfirmationEmail(t.getEmail(), "Verification code" + verifcode);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Account Admin created successfully!");
        //afficherAlert("Account Admin created successfully!", Alert.AlertType.INFORMATION);
    }


    public void UpdateAdmin(User t, int id) {
        String req = "update USER set full_name = ?,email = ?,telephone = ?,login = ?,password = ? where id = ? ";
        try {
            pst = cnx.prepareStatement(req);

            pst.setString(1, t.getFullName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getTelephone());
            pst.setString(4, t.getLogin());
            pst.setString(5, BCrypt.hashpw(t.getPassword(), BCrypt.gensalt(12)));
            pst.setInt(6, id);
            pst.executeUpdate();
            System.out.println("Admin: " + t.getFullName() + " successfully updated");
            // afficherAlert("Admin: "+t.getFullName()+" successfully updated", Alert.AlertType.INFORMATION);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void UpdateAdmin2(User t, int id) {
        String req = "update USER set full_name = ?,email = ?,telephone = ?,login = ? where id = ? ";
        try {
            pst = cnx.prepareStatement(req);

            pst.setString(1, t.getFullName());
            pst.setString(2, t.getEmail());
            pst.setString(3, t.getTelephone());
            pst.setString(4, t.getLogin());
            pst.setInt(5, id);
            pst.executeUpdate();
            System.out.println("Admin: " + t.getFullName() + " successfully updated");
            //  afficherAlert("Admin: "+t.getFullName()+" successfully updated", Alert.AlertType.INFORMATION);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public void DeleteAdmin(int id) {
        String req = "delete from USER where id =" + id;
        try {
            ste = cnx.createStatement();
            //pst.setInt(1,id);
            ste.executeUpdate(req);
            System.out.println("Account Admin successfully deleted");
            afficherAlert("Account Admin successfully deleted", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public void DeleteForEver(int id) {
        String req = "DELETE FROM USER WHERE id=" + id;
        try {
            ste = cnx.createStatement();
            ste.addBatch("SET FOREIGN_KEY_CHECKS=OFF");
            ste.addBatch(req);
            ste.addBatch("SET FOREIGN_KEY_CHECKS=ON");
            //pst.setInt(1,id);
            ste.executeBatch();
            System.out.println("Account Admin successfully deleted");
            //afficherAlert("Account Admin successfully deleted", Alert.AlertType.INFORMATION);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public ObservableList<User> GetAllUser() {

        String req = "SELECT u.* FROM USER as u left join CLIENT as c on c.id_user=u.id where c.id_user is NULL ";
        ObservableList<User> list = FXCollections.observableArrayList();
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            while (rs.next()) {
                list.add(new User(rs.getInt("id"), rs.getString("full_name"), rs.getString("email"), rs.getString("telephone"), rs.getString("login"), rs.getString("password")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }


    public int RestPassword(String mail) {
        String req = "SELECT * FROM USER where email='" + mail + "'";
        String telephone = null;
        int tt = 0;
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                telephone = rs.getString("telephone");
                tt = rs.getInt("id");
                System.out.println("test   " + rs.getInt("id"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        int ran = new Random().nextInt((90000 - 10000) + 1) + 10000;
        this.coderest = ran;
        System.out.println(ran);
        if (tt != 0) {
            sendsms(telephone, ran);
            System.out.println("Code successfully sent");
            afficherAlert("Code successfully sent", Alert.AlertType.INFORMATION);
        } else {
            System.out.println("No Email Found");
            afficherAlert("No Email Found", Alert.AlertType.WARNING);
        }
        return tt;
    }

    public int LoginIsClient(String login, String pwd) throws SQLException {
        boolean t = false;

        String req2 = "SELECT u.* FROM USER as u inner join CLIENT as c on u.id=c.id_user where u.login='" + login + "' ";
        try {

            ste = cnx.createStatement();
            rs = ste.executeQuery(req2);

            while (rs.next()) {
                t = true;
                String pass = rs.getString("u.password");
                if (BCrypt.checkpw(pwd, pass) && LoginIsAdmin(login) && rs.first()) {
                    return 1;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("+ + + " + LoginIsAdmin(login));
        if (LoginIsAdmin(login) && !t)
            return 2;
        return 0;
    }

    public boolean LoginIsAdmin(String login) throws SQLException {
        String req3 = "SELECT u.* FROM USER as u inner join VERIFICATION_CODE as v on u.id=v.user_id where u.login='" + login + "' ";
        ste = cnx.createStatement();
        rs = ste.executeQuery(req3);
        return rs.first();
    }

    public void afficherAlert(String message, Alert.AlertType t) {
        Alert alert = new Alert(t);
        alert.setTitle("Information Dialog");
        //alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public boolean isVerified(String login, String pwd) {

        String req2 = "SELECT u.* FROM USER as u inner join VERIFICATION_CODE as v on u.id=v.user_id where u.login='" + login + "'  ";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req2);
            while (rs.next()) {
                String pass = rs.getString("u.password");
                if (BCrypt.checkpw(pwd, pass)) {
                    idverif = rs.getInt("u.id");
                    System.out.println(idverif);
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;

    }

    public int getId(String login) {

        String req2 = "SELECT * FROM USER  where login='" + login + "'  ";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req2);
            while (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;

    }

    public boolean VerifLogin(String login, int id) throws SQLException {
        String req2 = "SELECT * FROM USER where login='" + login + "'  ";
        ste = cnx.createStatement();
        rs = ste.executeQuery(req2);
        while (rs.next()) {
            int idd = rs.getInt("id");
            if (id != idd) {
                return true;
            }
        }
        return false;
    }

    public boolean VerifEmail(String email, int id) throws SQLException {
        String req2 = "SELECT * FROM USER where email='" + email + "'  ";
        ste = cnx.createStatement();
        rs = ste.executeQuery(req2);
        while (rs.next()) {
            int idd = rs.getInt("id");
            if (id != idd) {
                return true;
            }
        }
        return false;
    }

    public boolean exist(String login, String pwd) throws SQLException {
        String req2 = "SELECT * FROM USER where login='" + login + "'  ";
        ste = cnx.createStatement();
        rs = ste.executeQuery(req2);
        while (rs.next()) {
            String pass = rs.getString("password");
            System.out.println(rs.getString("login") + "  sss  " + pass);
            System.out.println(BCrypt.checkpw(pwd, pass));
            if (BCrypt.checkpw(pwd, pass)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User LoginUser(String login, String pwd) {
        User u = null;
        int verif;
        String req = "SELECT * FROM USER where login='" + login + "' ";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);

            while (rs.next()) {
                System.out.println(rs.first());
                System.out.println(LoginIsClient(login, pwd));
                verif = LoginIsClient(login, pwd);
                int IdUser = rs.getInt("u.id");
                String Full_name = rs.getString("u.full_name");
                String email = rs.getString("u.email");
                String telephone = rs.getString("u.telephone");
                String adresse = "";
                int zip_code = 0;
                String log = rs.getString("u.login");
                String pass = rs.getString("u.password");
                System.out.println(verif);
                if (LoginIsClient(login, pwd) != 0) {
                    switch (verif) {
                        case 0:
                            u = null;
                            return u;
                        case 1:
                            u = new Client(IdUser, Full_name, email, telephone, log, pass, adresse, zip_code);
                            System.out.println("Hello User Client");
                            return u;
                        case 2:
                            u = new User(IdUser, Full_name, email, telephone, log, pass);
                            System.out.println("Hello User Admin");
                            return u;
                        default:
                            break;
                    }
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }

    @Override
    public Boolean VerifAccountUser(int id, String verification_code) {

        Boolean v = false;
        String req = "SELECT * FROM VERIFICATION_CODE where code='" + verification_code + "' and user_id='" + id + "' ";
        try {
            ste = cnx.createStatement();
            rs = ste.executeQuery(req);
            v = rs.first();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        if (!v) {
            AddCode(verification_code, id);
            System.out.println("Your Account has been verified");
            afficherAlert("Your Account has been verified", Alert.AlertType.INFORMATION);
            return true;
        } else
            System.out.println("Your Account already verified");
        afficherAlert("Your Account already verified", Alert.AlertType.WARNING);
        return false;
    }

    @Override
    public void AddCode(String code, int user_id) {


        String req = "Insert into VERIFICATION_CODE (code,user_id)  values (?,?)";
        try {
            pst = cnx.prepareStatement(req);
            pst.setString(1, code);
            pst.setInt(2, user_id);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Your Account code verification Added");
    }


    public void UpdateRestPassword(int code, String password, int id) {


        System.out.println(this.coderest);
        if (id != 0 && this.coderest == code) {
            String req = "update USER set password = ? where id = ?  ";
            try {
                pst = cnx.prepareStatement(req);
                pst.setString(1, BCrypt.hashpw(password, BCrypt.gensalt(12)));
                pst.setInt(2, id);
                pst.executeUpdate();
                System.out.println("New password has been set");
                afficherAlert("New password has been set", Alert.AlertType.INFORMATION);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else
            System.out.println("Verifier votre code");
    }

    @Override
    public void mailling(String mail, String message) {
        final String username = "nourabes12@gmail.com";
        final String password = "0123azertyuiop";
        String fromEmail = "nourabes12@gmail.com";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(fromEmail));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
            msg.setSubject("User Verification");
            Multipart emailContent = new MimeMultipart();
            MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(message);
            emailContent.addBodyPart(textBodyPart);
            msg.setContent(emailContent);
            Transport.send(msg);
            System.out.println("Sent message");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendsms(String str, int body) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                new PhoneNumber("+216" + str), // To number
                new PhoneNumber("+13236735509"), // From number
                "Verification code to reset password is :" + body
        ).create();
    }

    @Override
    public User GetUser(int id) {
        User u = null;
        try {
            String req = "SELECT * FROM USER where id="+id;
            pst = cnx.prepareStatement(req);
            rs = pst.executeQuery(req);
            while (rs.next()) {
                return(new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),rs.getString(6),rs.getInt(7)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return u;
    }
}
