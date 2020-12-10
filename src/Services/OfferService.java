package Services;

import Beans.Offer;
import Beans.User;
import Dao.IService;
import Utility.Global;
import Utility.Singleton;
import api.SMS_Service;

import java.sql.*;
import java.util.ArrayList;

public class OfferService implements IService<Offer> {
    private final Connection cnx = Singleton.getConn();

    @Override
    public void add(Offer offer) {
        try {
            String request = "insert into OFFER (user_id, title, description, author, price, image) values (?,?,?,?,?,?)";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, Global.getCurrentUser().getId());
            st.setString(2, offer.getTitle());
            st.setString(3, offer.getDescription());
            st.setString(4, offer.getAuthor());
            st.setFloat(5, offer.getPrice());
            st.setString(6, offer.getImage());
            st.executeUpdate();
            System.out.println("Offer added successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        try {
            String request = "DELETE FROM OFFER where id = ?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, id);
            st.executeUpdate();
            System.out.println("Offer deleted successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public String getSeller (int id) {
        try {
            String request = "SELECT full_name from USER where id = ?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) return result.getString(1);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Offer offer) {
        try {
            String request = "UPDATE OFFER set title = ?, description = ?, author = ?, price = ?  where id = ?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setString(1, offer.getTitle());
            st.setString(2, offer.getDescription());
            st.setString(3, offer.getAuthor());
            st.setFloat(4, offer.getPrice());
            st.executeUpdate();
            SMS_Service sms_service = new SMS_Service();

            System.out.println("Offer updated successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public Offer get (int id) {
        try {
            String request = "SELECT * from OFFER where id = ?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return new Offer(
                        resultSet.getInt(1),
                        new User(resultSet.getInt(2)),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getTimestamp(9)
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    public int countOffers () {
        try {
            String request = "SELECT COUNT(*) FROM OFFER";
            PreparedStatement st = cnx.prepareStatement(request);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) return resultSet.getInt(1);

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return 0;
    }



    @Override
    public ArrayList<Offer> get () {
        ArrayList<Offer> offers = new ArrayList<>();
        try {
            String request = "SELECT * FROM OFFER";
            PreparedStatement st = cnx.prepareStatement(request);
            ResultSet resultSet = st.executeQuery();
            System.out.println(resultSet);
            while (resultSet.next()) {
                offers.add(
                 new Offer(
                         resultSet.getInt(1),
                         new User(resultSet.getInt(2)),
                         resultSet.getString(3),
                         resultSet.getString(4),
                         resultSet.getString(5),
                         resultSet.getFloat(6),
                         resultSet.getString(7),
                         resultSet.getString(8),
                         resultSet.getTimestamp(9)
                    )
                );
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return offers;
    }

    public void buy (Offer offer) {
        try {
            String request = "UPDATE OFFER SET bought = 'YES' WHERE id = ?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setInt(1, offer.getId());
            statement.executeUpdate();
        }catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void decline (Offer offer) {
        try {
            String request = "UPDATE OFFER SET bought = 'NO' WHERE id = ?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setInt(1, offer.getId());
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


}
