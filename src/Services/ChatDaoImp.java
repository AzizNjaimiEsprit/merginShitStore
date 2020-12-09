package Services;

import Beans.Chat;
import Utility.Singleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ChatDaoImp implements ChatDao<Chat> {
    Connection conn = Singleton.getConn();

    @Override
    public void ajouterElementChat(Chat chat) {
        try {
            for (Chat i : afficherChat()) {
                if (i.equals(chat)) {
                    System.out.println("Question and answer already exist!");
                    return;
                }
            }
            PreparedStatement st = conn.prepareStatement("INSERT INTO CHAT (question,reponse) VALUES(?,?)");
            st.setString(1, chat.getQuestion());
            st.setString(2, chat.getReponse());
            st.executeUpdate();
            System.out.println("Chat question and answer have been added successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String afficherReponse(Chat chat) {
        String reponse = null;
        try {
            PreparedStatement st = conn.prepareStatement("SELECT reponse from CHAT where id=? AND question=?");
            st.setInt(1, chat.getId());
            st.setString(2, chat.getQuestion());

            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                reponse = rs.getString("reponse");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return reponse;
    }

    @Override
    public void modifierChatItem(Chat chat) {
        try {
            System.out.println(chat);
            PreparedStatement st = conn.prepareStatement("UPDATE CHAT SET question=? ,reponse=? WHERE id=?");
            st.setString(1, chat.getQuestion());
            st.setString(2, chat.getReponse());
            st.setInt(3, chat.getId());
            st.executeUpdate();
            System.out.println("Chat item has been updated successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimerChatItem(Chat chat) {
        try {
            PreparedStatement preparedStmt = conn.prepareStatement("DELETE FROM CHAT WHERE id=? ");
            preparedStmt.setInt(1, chat.getId());
            int result = preparedStmt.executeUpdate();
            if (result != 0)
                System.out.println("Chat item has been removed Successfully!");
            else
                System.out.println("Failed to remove chat item, Please try again later!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Chat> afficherChat() {
        ObservableList<Chat> res = FXCollections.observableArrayList();
        ;
        try {
            PreparedStatement preparedStat = conn.prepareStatement("SELECT * FROM CHAT");
            ResultSet rs = preparedStat.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String question = rs.getString("question");
                String reponse = rs.getString("reponse");
                Chat chat = new Chat(id, question, reponse);
                res.add(chat);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public Chat getChatItem(Chat chat) {
        Chat returnedChat = null;
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM CHAT WHERE question=?");
            preparedStatement.setString(1, chat.getQuestion());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                returnedChat = new Chat(rs.getInt("id"), chat.getQuestion(), chat.getReponse());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnedChat;
    }
}
