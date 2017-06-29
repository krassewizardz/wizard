package wizard.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wizard.ViewManager;
import wizard.models.View;
import wizard.services.SQLiteAuthenthicationService;
import wizard.services.TranslationService;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public Label loginLbl, usernameLbl, passwordLbl, messageLbl;

    @FXML
    public TextField usernameTxt;

    @FXML
    public PasswordField passwordTxt;

    @FXML
    public Button guestBtn, loginBtn;

    private ViewManager viewManager = ViewManager.getInstance();
    private SQLiteAuthenthicationService sqLiteAuthenthicationService = SQLiteAuthenthicationService.getInstance();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        loginLbl.setText(TranslationService.translate("views.login.loginLbl"));
        usernameLbl.setText(TranslationService.translate("views.login.usernameLbl"));
        passwordLbl.setText(TranslationService.translate("views.login.passwordLbl"));
        guestBtn.setText(TranslationService.translate("views.login.guestBtn"));
        loginBtn.setText(TranslationService.translate("views.login.loginBtn"));
    }

    //TODO normaler user wird auf overview gebracht, admin zu registration
    public void onLogin() {
        if (formIsValid() && sqLiteAuthenthicationService.login(usernameTxt.getText(), passwordTxt.getText())) {
            if(usernameTxt.getText().toLowerCase().equals("admin")) {
                viewManager.display(View.REGISTRATION);
            } else {
                viewManager.display(View.OVERVIEW);
            }
        } else {
            messageLbl.setText(TranslationService.translate("views.login.messageLbl"));
        }
    }

    public void onGuestLogin() {
        viewManager.setLoggedIn(false);
        viewManager.display(View.OVERVIEW);
    }

    public boolean formIsValid() {
        return (usernameTxt.getText().length() >= 4 && passwordTxt.getText().length() >= 4);
    }

    public void updateFormValidationState() {
        messageLbl.setText("");
        loginBtn.setDisable(!formIsValid());
    }
}
