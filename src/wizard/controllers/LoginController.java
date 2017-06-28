package wizard.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import wizard.ViewManager;
import wizard.services.JSONConfigService;
import wizard.services.SQLiteAuthenthicationService;
import wizard.services.TranslationService;
import wizard.utility.KeyNotFoundException;

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

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        loginLbl.setText(TranslationService.translate("views.login.loginLbl"));
        usernameLbl.setText(TranslationService.translate("views.login.usernameLbl"));
        passwordLbl.setText(TranslationService.translate("views.login.passwordLbl"));
        guestBtn.setText(TranslationService.translate("views.login.guestBtn"));
        loginBtn.setText(TranslationService.translate("views.login.loginBtn"));
    }

    public void onLogin() {
        if (formIsValid()) {

            if (
                    SQLiteAuthenthicationService.getInstance()
                    .login(usernameTxt.getText(), passwordTxt.getText())
            ) {
                ViewManager.getInstance().display(ViewManager.View.OVERVIEW);
            } else {
                messageLbl.setText(TranslationService.translate("error_login_invalid"));
            }

        }
    }

    public void onGuestLogin() {
        ViewManager vm = ViewManager.getInstance();
        vm.setGuestMode(true);
        vm.display(ViewManager.View.OVERVIEW);
    }

    public boolean formIsValid() {
        return (usernameTxt.getText().length() >= 4 && passwordTxt.getText().length() >= 4);
    }

    public void updateFormValidationState() {
        messageLbl.setText("");
        loginBtn.setDisable(!formIsValid());
    }

}
