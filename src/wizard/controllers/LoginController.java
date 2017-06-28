package wizard.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wizard.ViewManager;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Label titleLbl, usernameLbl, passwordLbl;

    @FXML
    public TextField usernameTxt;

    @FXML
    public PasswordField passwordTxt;

    @FXML
    public Button guestBtn, loginBtn;

    private ViewManager viewManager = ViewManager.getInstance();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        titleLbl.setText("Login");
        usernameLbl.setText("Benutzername");
        passwordLbl.setText("Passwort");
        guestBtn.setText("Gastlogin");
        loginBtn.setText("Login");
    }

    public void login(ActionEvent actionEvent) {
        try {
            String username = usernameTxt.getText();
            System.out.println(username);
            String password = passwordTxt.getText();
            boolean isValid = this.validateUserInput(username, password);

            if(isValid) {
                if(username.toLowerCase().equals("admin".toLowerCase())) {
                    viewManager.display(ViewManager.View.REGISTRATION);
                } else {
                    viewManager.setIsLoggedIn(true);
                    viewManager.display(ViewManager.View.OVERVIEW);
                }
            } else {
                //TODO Fehlermeldung ausgeben
                System.out.println("Falsche benutzerdaten");
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
            e.printStackTrace();
        }

    }

    public void loginAsGuest(ActionEvent actionEvent) {
        viewManager.setIsLoggedIn(false);
        viewManager.display(ViewManager.View.OVERVIEW);
    }

    public boolean validateUserInput(String username, String password) {
        //TODO mit userdatenbank validieren
        return true;
    }

}
