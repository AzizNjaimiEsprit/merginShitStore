package Views.Controllers;

import javafx.scene.control.Alert;

import java.util.Calendar;

/**
 *
 * @author  Njaimi Med Aziz
 */

public class InputControlOrders {
    Alert alert = new Alert(Alert.AlertType.ERROR);

    public InputControlOrders() {
        alert.setTitle("Error");
    }

    public boolean checkAddOrder1 (String address, String codePostal, String numTel){
        if (address.length() < 10){
            showAlert("Invalid Address");
            return false;
        }
        if (codePostal.length() != 4){
            showAlert("Invalid ZipCode");
            return false;
        }else{
            try{
                Integer.parseInt(codePostal);
            }catch (NumberFormatException ex){
                showAlert("Invalid ZipCode");
                return false;
            }
        }
        if (numTel.length() != 8){
            showAlert("Invalid Phone Number");
            return false;
        }else{
            try{
                Integer.parseInt(numTel);
            }catch (NumberFormatException ex){
                showAlert("Invalid Phone Number");
                return false;
            }
        }
        return true;
    }
    public boolean checkAddOrder2 (String fullName,String cardNum,String expM,String expY,String email){
        // Checking FullName
        if (fullName.length() < 5){
            showAlert("Invalid Name");
            return false;
        }
        // Checking Card Number
        if (cardNum.length() != 16){
            showAlert("Invalid Card Number");
            return false;
        }else{
            try{
                Long.parseLong(cardNum);
            }catch (NumberFormatException ex){
                showAlert("Invalid Card Number");
                return false;
            }
        }
        // Checking Expiration Month
        if (expM.length() > 2 || expM.length() <= 0){
            showAlert("Invalid Expiration Month");
            return false;
        }else{
            try{
                int x = Integer.parseInt(expM);
                if (x <= 0 || x> 12) Integer.parseInt("break");
            }catch (NumberFormatException ex){
                showAlert("Invalid Expiration Month");
                return false;
            }
        }
        // Checking Expiration Year
        if (expY.length() != 4 ){
            showAlert("Invalid Expiration Year");
            return false;
        }else{
            try{
                int x = Integer.parseInt(expY);
                if (x <= 0 || x < Calendar.getInstance().get(Calendar.YEAR)) Integer.parseInt("break");
            }catch (NumberFormatException ex){
                showAlert("Invalid Expiration Year");
                return false;
            }
        }
        // Checking Email
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        if (email.length()<10 || email.matches(regex) == false){
            showAlert("Invalid Email");
            return false;
        }

        return true;
    }

    public void showAlert(String text){
        alert.setHeaderText(text);
        alert.showAndWait();
    }

}
