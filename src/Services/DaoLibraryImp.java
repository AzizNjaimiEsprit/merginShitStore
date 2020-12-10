package Services;

import Beans.Library;
import Beans.OnlineBook;
import Beans.User;
import Dao.DaoLibrary;
import Utility.Singleton;
import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DaoLibraryImp implements DaoLibrary<Library> {
    Connection conn = Singleton.getConn();
    CrudOnlineBook crudOnlineBook = new CrudOnlineBook();

    @Override
    public void addToLibrary(Library library) {
        try {
            //preparing the statement
            PreparedStatement preparedStat = conn.prepareStatement("INSERT INTO LIBRARY VALUES(?,?,?,?,?)");
            preparedStat.setInt(1, library.getUser().getId());
            preparedStat.setInt(2, library.getBook().getId());
            preparedStat.setString(3, "new");
            preparedStat.setInt(4, 0);
            preparedStat.setInt(5, 0);
            //executing the request
            preparedStat.executeUpdate();
            System.out.println("Book has been added to your library Successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void removeLibraryBook(Library library) {
        try {
            PreparedStatement preparedStmt = conn.prepareStatement("DELETE FROM LIBRARY WHERE book_id= ? AND user_id=? ");
            preparedStmt.setInt(1, library.getBook().getId());
            preparedStmt.setInt(2, library.getUser().getId());
            int result = preparedStmt.executeUpdate();
            if (result != 0)
                System.out.println("Book has been removed Successfully!");
            else
                System.out.println("Failed to remove book, Please try again later!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public ObservableList<Library> getLibraryitems(int userId) {

        ObservableList<Library> res = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStat = conn.prepareStatement("SELECT book_id,status,page_reached,quiz_score from LIBRARY where user_id= ?");
            preparedStat.setInt(1, userId);
            ResultSet rs = preparedStat.executeQuery();
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String status = rs.getString("status");
                int pageReached = rs.getInt("page_reached");
                int quizScore = rs.getInt("quiz_score");
                OnlineBook onlineBook = crudOnlineBook.RecupererLivreEnLigneByid(bookId);
                Library newLibrary = new Library(onlineBook, status, pageReached, quizScore);
                res.add(newLibrary);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public Library getLibraryitem(Library library) {
        Library existingLibrary = null;
        try {
            PreparedStatement preparedStat = conn.prepareStatement("SELECT * from LIBRARY where user_id= ? AND book_id=?");
            preparedStat.setInt(1, library.getUser().getId());
            preparedStat.setInt(2, library.getBook().getId());
            ResultSet rs = preparedStat.executeQuery();
            if (rs.next()) {
                String status = rs.getString("status");
                int pageReached = rs.getInt("page_reached");
                int quizScore = rs.getInt("quiz_score");
                OnlineBook onlineBook = crudOnlineBook.RecupererLivreEnLigneByid(library.getBook().getId());
                existingLibrary = new Library(onlineBook, status, pageReached, quizScore);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return existingLibrary;
    }

    @Override
    public String updateReachedPage(Library library) {
        String newStatus = library.getStatus();
        try {
            if (library.getReachedPage() == 0) {
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE LIBRARY SET page_reached=?,status='Recently added' WHERE book_id=?");
                preparedStatement.setInt(1, library.getReachedPage());
                preparedStatement.setInt(2, library.getBook().getId());
                preparedStatement.executeUpdate();
                newStatus = "Recently added";
            } else if (library.getReachedPage() == crudOnlineBook.RecupererLivreEnLigneByid(library.getBook().getId()).getNbPage()) {
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE LIBRARY SET page_reached=?,status='Finished' WHERE book_id=?");
                preparedStatement.setInt(1, library.getReachedPage());
                preparedStatement.setInt(2, library.getBook().getId());
                preparedStatement.executeUpdate();
                newStatus = "Finished";
            } else {
                PreparedStatement preparedStatement = conn.prepareStatement("UPDATE LIBRARY SET page_reached=?,status='On progress' WHERE book_id=?");
                preparedStatement.setInt(1, library.getReachedPage());
                preparedStatement.setInt(2, library.getBook().getId());
                preparedStatement.executeUpdate();
                newStatus = "On progress";
            }
            System.out.println("Page reached modified!");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newStatus;
    }

    @Override
    public void updateQuizScore(Library library) {
        try {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE LIBRARY SET quiz_score=? WHERE user_id=? AND book_id=?");
            preparedStatement.setInt(1, library.getQuizScore());
            preparedStatement.setInt(2, library.getUser().getId());
            preparedStatement.setInt(3, library.getBook().getId());
            preparedStatement.executeUpdate();
            System.out.println("Quiz score has been updated!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendSms(User user, Library library) {
        try {
            final String ACCOUNT_SID = "AC4bde583360226b8f1ede5b56f62ebe27";
            final String AUTH_TOKEN = "ae37ac8cfadd40cdf9d865831f1bb325";
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                    new PhoneNumber("+216" + user.getTelephone()), // To number
                    new PhoneNumber("+17658964442"), // From number
                    "Congratulations you have received : " + library.getQuizScore() * 10 + " fidelity points from the quiz"
            ).create();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
