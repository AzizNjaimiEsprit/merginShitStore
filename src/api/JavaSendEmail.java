package api;

import Beans.Book;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaSendEmail {
    public static void SendMail (String receipient , Book B  ){
        System.out.println("preparation d'un email");
        Properties p = new Properties();
        p.put("mail.smtp.auth","true");
        p.put("mail.smtp.starttls.enable","true");
        p.put("mail.smtp.host","smtp.gmail.com");
        p.put("mail.smtp.port","587");

        String myEmail = "bookstore.cm.esprit@gmail.com";
        String myPassword = "bookstore1234";

        Session session = Session.getInstance(p,new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(myEmail,myPassword);
            }
        });
        Message message = prepareMessage(session , myEmail , receipient , B);
        try {
            Transport.send(message);
            System.out.println("email est envoyer ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Message prepareMessage(Session session, String myEmail , String receipient , Book B ) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(receipient));
            message.setSubject("Alerte : Repture du stock ");
            message.setText(" Bonjour Admin \n Le Stock du livre N°"+B.getId()+" est en repture du stock "+"\n ** DETAIL DU LIVRE ** \n"+B.toString()+"`\n"+"Cordialement");
            return message;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
