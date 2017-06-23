package wizard.controllers;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wizard.ViewManager;

import javax.xml.soap.Text;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by fpr on 23.06.2017.
 */
public class RegistrationController implements Initializable {

    @FXML
    Button registerBtn, backBtn;

    @FXML
    Label titleLbl, usernameLbl, passwordLbl, passwordRepeatLbl;

    @FXML
    TextField usernametTxt;

    @FXML
    PasswordField passwordTxt, passwordRepeatTxt;

    private ViewManager viewManager = ViewManager.getInstance();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        titleLbl.setText("Benutzer anlegen");
        registerBtn.setText("Anlegen");
        backBtn.setText("Zurück");
        usernameLbl.setText("Benutzername");
        passwordLbl.setText("Passwort");
        passwordRepeatLbl.setText("Passwort wiederholen");
    }

    public void back() {
        viewManager.gotoLogin();
    }

    public void register() {
        //TODO user anlegen
        System.out.println("Should create user here");
        registerBtn.setText("saved");
    }
}
