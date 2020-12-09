package Views.Controllers;

import javafx.scene.control.Alert;

public class InputControlLibrary {
    Alert alert = new Alert(Alert.AlertType.ERROR);

    public InputControlLibrary() {
        alert.setTitle("Error");
    }

    public boolean checkQuizInput(String q1, String q2, String q3, String answer1, String answer2, String answer3,
                                  String answer4, String answer5, String answer6, String answer7, String answer8, String answer9,
                                  String correct1, String correct2, String correct3) {
        if (q1.length() <= 0) {
            showAlert("Invalid question 1");
            return false;
        }

        if (q2.length() <= 0) {
            showAlert("Invalid question 2");
            return false;
        }
        if (q3.length() <= 0) {
            showAlert("Invalid question 3");
            return false;
        }

        if (answer1.length() <= 0) {
            showAlert("Invalid answer 1 for question 1");
            return false;
        }

        if (answer2.length() <= 0) {
            showAlert("Invalid answer 2 for question 1");
            return false;
        }
        if (answer3.length() <= 0) {
            showAlert("Invalid answer 2 for question 3");
            return false;
        }

        if (answer4.length() <= 0) {
            showAlert("Invalid answer 1 for question 2");
            return false;
        }

        if (answer5.length() <= 0) {
            showAlert("Invalid answer 2 for question 2");
            return false;
        }
        if (answer6.length() <= 0) {
            showAlert("Invalid answer 2 for question 2");
            return false;
        }

        if (answer7.length() <= 0) {
            showAlert("Invalid answer 1 for question 3");
            return false;
        }

        if (answer8.length() <= 0) {
            showAlert("Invalid answer 2 for question 3");
            return false;
        }
        if (answer9.length() <= 0) {
            showAlert("Invalid answer 2 for question 3");
            return false;
        }

        if (correct1.length() != 1) {
            showAlert("Invalid correct answer for question 1");
            return false;
        } else {
            try {
                Integer.parseInt(correct1);
            } catch (NumberFormatException ex) {
                showAlert("Invalid correct answer for question 1");
                return false;
            }
        }

        if (correct2.length() != 1) {
            showAlert("Invalid correct answer for question 2");
            return false;
        } else {
            try {
                Integer.parseInt(correct2);
            } catch (NumberFormatException ex) {
                showAlert("Invalid correct answer for question 2");
                return false;
            }
        }

        if (correct3.length() != 1) {
            showAlert("Invalid correct answer for question 3");
            return false;
        } else {
            try {
                Integer.parseInt(correct3);
            } catch (NumberFormatException ex) {
                showAlert("Invalid correct answer for question 3");
                return false;
            }
        }
        return true;
    }


    public void showAlert(String text) {
        alert.setTitle("Input error");
        alert.setHeaderText(text);
        alert.showAndWait();
    }

}
