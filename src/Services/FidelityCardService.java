package Services;

import Beans.FidelityCard;
import Beans.User;
import Utility.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FidelityCardService implements IService<FidelityCard>{
    private final Connection cnx = Singleton.getConn();

    @Override
    public void add (FidelityCard card) {
        try {
            String request = "INSERT INTO FIDELITYCARD value (null, ?, ?)";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setInt(1, card.getUser().getId());
            statement.setInt(2, card.getPoints());
            statement.executeUpdate();
            System.out.println("Fidelity Card added successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void delete (int userID) {
        try {
            String request = "DELETE FROM FIDELITYCARD where user_id = ?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setInt(1, userID);
            statement.executeUpdate();
            System.out.println("Fidelity Card deleted successfully");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public void update (FidelityCard card) {
        try {
            String request = "UPDATE FIDELITYCARD SET points = ? where user_id = ?";
            PreparedStatement statement = cnx.prepareStatement(request);
            statement.setInt(1, card.getPoints());
            statement.setInt(2, card.getUser().getId());
            statement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    @Override
    public FidelityCard get (int userID) {
        try {
            String request = "SELECT * from FIDELITYCARD where user_id = ?";
            PreparedStatement st = cnx.prepareStatement(request);
            st.setInt(1, userID);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return (new FidelityCard(
                        resultSet.getInt(1),
                        new User(resultSet.getInt(2)),
                        resultSet.getInt(3)
                ));
            }
            resultSet.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<FidelityCard> get () {
        ArrayList<FidelityCard> cards = new ArrayList<>();
        try {
            String request = "SELECT * FROM FIDELITYCARD";
            PreparedStatement statement = cnx.prepareStatement(request);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                cards.add(
                        new FidelityCard(
                                result.getInt(1),
                                new User(result.getInt(2)),
                                result.getInt(3)
                        )
                );
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return cards;
    }



}
