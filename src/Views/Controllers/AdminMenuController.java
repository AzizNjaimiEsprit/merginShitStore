package Views.Controllers;

import Beans.Client;
import Beans.User;
import Services.UserService;
import Services.clientService;
import Utility.Global;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminMenuController implements Initializable {
    @FXML
    private MenuBar menuBar;

    public TabPane menutab;
    public Tab adminTab;
    public Tab clientTab;
    public TextField fullAdmin;
    public TextField emailAdmin;
    public TextField phoneAdmin;
    public TextField loginAdmin;
    public PasswordField passwrdAdmin;
    public PasswordField RpasswordAdmin;
    public Button AddAdmin;
    public Button UpdateAdmin;
    public Button DeleteAdmin;
    public Button AddFAdmin;
    //////////////////// Client
    public TextField fullClient;
    public TextField emailClient;
    public TextField phoneClient;
    public TextField loginClient;
    public PasswordField passwrdClient;
    public PasswordField RpasswordClient;
    public Button UpdateClient;
    public Button DeleteClient;
    public TextField AdresseClient;
    public TextField ZipClient;
    public Button logout;
    public AnchorPane AnchorAdmin;
    public ImageView image;
    @FXML
    private TableView<User> admin_table;
    @FXML
    private TableColumn<User, String> id_admin;
    @FXML
    private TableColumn<User, String> full_col_admin;
    @FXML
    private TableColumn<User, String> email_admin;
    @FXML
    private TableColumn<User, String> phone_admin;
    @FXML
    private TableColumn<User, String> login_admin;
    @FXML
    private TableColumn<User, String> password_admin;
    /////////////////////////////////////////////////////////
    @FXML
    private TableView<Client> client_table;
    @FXML
    private TableColumn<Client, String> id_client;
    @FXML
    private TableColumn<Client, String> full_client;
    @FXML
    private TableColumn<Client, String> email_client;
    @FXML
    private TableColumn<Client, String> phone_client;
    @FXML
    private TableColumn<Client, String> address_client;
    @FXML
    private TableColumn<Client, String> zip_client;
    @FXML
    private TableColumn<Client, String> login_client;
    @FXML
    private TableColumn<Client, String> password_client;

    UserService us = new UserService();
    clientService cs = new clientService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //initMenuBar(menuBar);

        image = new ImageView(new File("Views/Ressources/Images/iconelogout.png").toURI().toString());

        // id_admin.setCellValueFactory(new PropertyValueFactory<>("id"));
        full_col_admin.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        email_admin.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone_admin.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        login_admin.setCellValueFactory(new PropertyValueFactory<>("login"));
        // password_admin.setCellValueFactory(new PropertyValueFactory<>("password"));
        System.out.println(us.GetAllUser());
        admin_table.setItems(us.GetAllUser());
        ///////////////////////////////////////////////////////////////////
        // id_client.setCellValueFactory(new PropertyValueFactory<>("idClient"));
        full_client.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        email_client.setCellValueFactory(new PropertyValueFactory<>("email"));
        phone_client.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        address_client.setCellValueFactory(new PropertyValueFactory<>("address"));
        zip_client.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        login_client.setCellValueFactory(new PropertyValueFactory<>("login"));
        // password_client.setCellValueFactory(new PropertyValueFactory<>("password"));
        System.out.println(cs.GetAllClient());
        client_table.setItems(cs.GetAllClient());
        SelectClient();
        Visibility();
        SelectAdmin();
    }

    public void afficherAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Information Dialog");
        //alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public boolean testSaisieAdmin() {
        if (
                fullAdmin.getText().trim().isEmpty()
                        || phoneAdmin.getText().trim().isEmpty()
                        || emailAdmin.getText().trim().isEmpty()
        ) {
            afficherAlert("All fields must be completed!!", Alert.AlertType.ERROR);
            return false;
        }
        Pattern err = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        if (!err.matcher(emailAdmin.getText()).matches()) {
            afficherAlert("Check your email format!!", Alert.AlertType.ERROR);
            return false;
        }
        if (phoneAdmin.getText().length() != 8) {
            afficherAlert("Phone Number must contains 8 numbers", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double num1 = Double.parseDouble(phoneAdmin.getText());
        } catch (NumberFormatException e) {
            afficherAlert("Invalid number fields", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    public boolean testSaisieClient() {
        if (
                fullClient.getText().trim().isEmpty()
                        || phoneClient.getText().trim().isEmpty()
                        || emailClient.getText().trim().isEmpty()
                        || AdresseClient.getText().trim().isEmpty()
                        || ZipClient.getText().trim().isEmpty()
        ) {
            afficherAlert("All fields must be completed!!", Alert.AlertType.ERROR);
            return false;
        }
        Pattern err = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        if (!err.matcher(emailClient.getText()).matches()) {
            afficherAlert("Check your email format!!", Alert.AlertType.ERROR);
            return false;
        }
        if (phoneClient.getText().length() != 8) {
            afficherAlert("Phone Number must contains 8 numbers", Alert.AlertType.ERROR);
            return false;
        }
        try {
            Double num = Double.parseDouble(ZipClient.getText());
            Double num1 = Double.parseDouble(phoneClient.getText());
        } catch (NumberFormatException e) {
            afficherAlert("Invalid number fields", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    public void AddAdminAction(ActionEvent actionEvent) {
        User u = new User(fullAdmin.getText(), emailAdmin.getText(), phoneAdmin.getText(), loginAdmin.getText(), passwrdAdmin.getText());
        if (testSaisieAdmin()) {
            if (passwrdAdmin.getText().equals(RpasswordAdmin.getText())) {
                us.AddAdmin(u);
                afficherAlert("Account Admin created successfully!", Alert.AlertType.INFORMATION);
                Visibility();
                admin_table.setItems(us.GetAllUser());
                admin_table.refresh();
            } else
                afficherAlert("Your password field must be equals to Repeat password field", Alert.AlertType.ERROR);
        }
    }

    public void UpdateAdminAction(ActionEvent actionEvent) throws SQLException {
        User u = admin_table.getSelectionModel().getSelectedItem();
        User u1 = new User(fullAdmin.getText(), emailAdmin.getText(), phoneAdmin.getText(), loginAdmin.getText(), passwrdAdmin.getText());
        if (testSaisieAdmin()) {
            if (!us.VerifLogin(loginAdmin.getText(), u.getId())) {
                if (!us.VerifEmail(emailAdmin.getText(), u.getId())) {
                    if (passwrdAdmin.getText().equals(RpasswordAdmin.getText())) {
                        if (!passwrdAdmin.getText().trim().isEmpty() || !RpasswordAdmin.getText().trim().isEmpty()) {
                            us.UpdateAdmin(u1, u.getId());
                            Visibility();
                            afficherAlert("Account: " + u.getFullName() + " successfully updated", Alert.AlertType.INFORMATION);
                            admin_table.setItems(us.GetAllUser());
                            admin_table.refresh();
                        } else {
                            us.UpdateAdmin2(u1, u.getId());
                            Visibility();
                            afficherAlert("Account: " + u.getFullName() + " successfully updated", Alert.AlertType.INFORMATION);
                            admin_table.setItems(us.GetAllUser());
                            admin_table.refresh();
                        }

                    } else
                        afficherAlert("Your password field must be equals to Repeat password field", Alert.AlertType.ERROR);
                } else
                    afficherAlert("Email already used", Alert.AlertType.ERROR);
            } else
                afficherAlert("Login already used", Alert.AlertType.ERROR);
        }

    }

    public void DeleteAdminAction(ActionEvent actionEvent) {
        User u = admin_table.getSelectionModel().getSelectedItem();
        us.DeleteAdmin(u.getId());
        Visibility();
        admin_table.setItems(us.GetAllUser());
        admin_table.refresh();
    }

    public void AddFAdminAction(ActionEvent actionEvent) {
        fullAdmin.setVisible(true);
        emailAdmin.setVisible(true);
        phoneAdmin.setVisible(true);
        loginAdmin.setVisible(true);
        passwrdAdmin.setVisible(true);
        RpasswordAdmin.setVisible(true);
        UpdateAdmin.setVisible(false);
        AddAdmin.setVisible(true);
        DeleteAdmin.setVisible(false);
        fullAdmin.clear();
        emailAdmin.clear();
        phoneAdmin.clear();
        loginAdmin.clear();
        passwrdAdmin.clear();
    }


    public void UpdateClientAction(ActionEvent actionEvent) throws SQLException {
        Client c = client_table.getSelectionModel().getSelectedItem();
        Client c1 = new Client(fullClient.getText(), emailClient.getText(), phoneClient.getText(), loginClient.getText(), passwrdClient.getText(), AdresseClient.getText(), Integer.parseInt(ZipClient.getText()));
        if (testSaisieClient()) {
            if (!us.VerifLogin(loginClient.getText(), c.getId())) {
                if (!us.VerifEmail(emailClient.getText(), c.getId())) {
                    if (passwrdClient.getText().equals(RpasswordClient.getText())) {
                        if (!passwrdClient.getText().trim().isEmpty() || !RpasswordClient.getText().trim().isEmpty()) {
                            us.UpdateAdmin(c1, c.getId());
                            cs.UpdateClient(AdresseClient.getText(), Integer.parseInt(ZipClient.getText()), c.getIdClient());
                            afficherAlert("Account: " + c.getFullName() + " successfully updated", Alert.AlertType.INFORMATION);
                            Visibility();
                            client_table.setItems(cs.GetAllClient());
                            client_table.refresh();
                        } else {
                            us.UpdateAdmin2(c1, c.getId());
                            cs.UpdateClient(AdresseClient.getText(), Integer.parseInt(ZipClient.getText()), c.getIdClient());
                            afficherAlert("Account: " + c.getFullName() + " successfully updated", Alert.AlertType.INFORMATION);
                            Visibility();
                            client_table.setItems(cs.GetAllClient());
                            client_table.refresh();
                        }
                    } else
                        afficherAlert("Your password field must be equals to Repeat password field", Alert.AlertType.ERROR);
                } else
                    afficherAlert("Email already used", Alert.AlertType.ERROR);
            } else
                afficherAlert("Login already used", Alert.AlertType.ERROR);
        }
    }

    public void DeleteClientAction(ActionEvent actionEvent) {
        Client c = client_table.getSelectionModel().getSelectedItem();
        System.out.println(c);
        us.DeleteForEver(c.getId());
        cs.DeleteClient(c.getIdClient());
        afficherAlert("Account Client successfully deleted", Alert.AlertType.INFORMATION);
        Visibility();
        client_table.setItems(cs.GetAllClient());
        client_table.refresh();

    }

    public void SelectAdmin() {
        admin_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                User u = admin_table.getSelectionModel().getSelectedItem();
                if (u != null) {
                    fullAdmin.setVisible(true);
                    emailAdmin.setVisible(true);
                    phoneAdmin.setVisible(true);
                    loginAdmin.setVisible(true);
                    passwrdAdmin.setVisible(true);
                    RpasswordAdmin.setVisible(true);
                    UpdateAdmin.setVisible(true);
                    AddAdmin.setVisible(false);
                    DeleteAdmin.setVisible(true);
                    fullAdmin.setText(u.getFullName());
                    emailAdmin.setText(u.getEmail());
                    phoneAdmin.setText(u.getTelephone());
                    loginAdmin.setText(u.getLogin());
                    //passwrdAdmin.setText(u.getPassword());
                } else
                    Visibility();
            }
        });
    }

    public void SelectClient() {
        client_table.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Client u = client_table.getSelectionModel().getSelectedItem();
                if (u != null) {
                    fullClient.setVisible(true);
                    emailClient.setVisible(true);
                    phoneClient.setVisible(true);
                    loginClient.setVisible(true);
                    passwrdClient.setVisible(true);
                    RpasswordClient.setVisible(true);
                    UpdateClient.setVisible(true);
                    DeleteClient.setVisible(true);
                    AdresseClient.setVisible(true);
                    ZipClient.setVisible(true);
                    fullClient.setText(u.getFullName());
                    emailClient.setText(u.getEmail());
                    phoneClient.setText(u.getTelephone());
                    loginClient.setText(u.getLogin());
                    ZipClient.setText(String.valueOf(u.getZipCode()));
                    AdresseClient.setText(u.getAddress());
                    //passwrdAdmin.setText(u.getPassword());
                } else
                    Visibility();
            }
        });
    }

    public void Visibility() {
        /////////////////////////////////////////////////////
        fullAdmin.setVisible(false);
        emailAdmin.setVisible(false);
        phoneAdmin.setVisible(false);
        loginAdmin.setVisible(false);
        passwrdAdmin.setVisible(false);
        RpasswordAdmin.setVisible(false);
        UpdateAdmin.setVisible(false);
        AddAdmin.setVisible(false);
        DeleteAdmin.setVisible(false);
        ////////////////////////////////////////////////////
        fullClient.setVisible(false);
        emailClient.setVisible(false);
        phoneClient.setVisible(false);
        loginClient.setVisible(false);
        passwrdClient.setVisible(false);
        RpasswordClient.setVisible(false);
        UpdateClient.setVisible(false);
        DeleteClient.setVisible(false);
        AdresseClient.setVisible(false);
        ZipClient.setVisible(false);
    }

    public void afficherAlert(String message, Alert.AlertType t) {
        Alert alert = new Alert(t);
        alert.setTitle("Information Dialog");
        //alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    public void ChangeInterface(String To, String Title) throws IOException {
        Stage stageq = (Stage) AnchorAdmin.getScene().getWindow();
        stageq.close();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(To));
        AnchorPane rootLayout = (AnchorPane) loader.load();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(rootLayout);
        stage.setTitle(Title);
        stage.setScene(scene);
        stage.show();
    }

    public void logout(ActionEvent mouseEvent) throws IOException {
        Global.setCurrentUser(null);
        ChangeInterface("/Views/Interfaces/login.fxml", "BookStore");

    }
}
