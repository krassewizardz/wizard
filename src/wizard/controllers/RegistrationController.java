package wizard.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wizard.ViewManager;
import wizard.models.User;
import wizard.models.View;
import wizard.repositories.UserRepository;
import wizard.services.SQLiteAuthenthicationService;
import wizard.services.TranslationService;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by fpr on 23.06.2017.
 */

public class RegistrationController implements Initializable {

    UserRepository userRepository = UserRepository.getInstance();
    SQLiteAuthenthicationService sqLiteAuthenthicationService = SQLiteAuthenthicationService.getInstance();

    @FXML
    Button registerBtn, backBtn;

    @FXML
    Label titleLbl, usernameLbl, passwordLbl, passwordRepeatLbl, messageLbl;

    @FXML
    TextField usernameTxt;

    @FXML
    PasswordField passwordTxt, passwordRepeatTxt;

    private ViewManager viewManager = ViewManager.getInstance();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        titleLbl.setText(TranslationService.translate("views.registration.titleLbl"));
        registerBtn.setText(TranslationService.translate("views.registration.registerBtn"));
        backBtn.setText(TranslationService.translate("views.registration.backBtn"));
        usernameLbl.setText(TranslationService.translate("views.registration.usernameLbl"));
        passwordLbl.setText(TranslationService.translate("views.registration.passwordLbl"));
        passwordRepeatLbl.setText(TranslationService.translate("views.registration.passwordRepeatLbl"));
    }

    public void back() {
        sqLiteAuthenthicationService.logout();
        viewManager.display(View.LOGIN);
    }

    public void register() {
        try {
            userRepository.add(new User("", usernameTxt.getText(), passwordTxt.getText()));
            messageLbl.setText(TranslationService.translate("views.registration.success"));
        } catch (Exception e) {
            messageLbl.setText(TranslationService.translate("views.registration.fail"));
        }
        usernameTxt.clear();
        passwordTxt.clear();
        passwordRepeatTxt.clear();
    }
}
