package wizard;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import wizard.models.View;
import wizard.services.JSONConfigService;
import wizard.services.TranslationService;
import wizard.utility.KeyNotFoundException;

import java.io.IOException;


/**
 *  @author F. Prinz
 *  @author F. Engels
 *  TODO@all: add documentation
 */
public class ViewManager {

    private Stage stage;
    private static ViewManager viewManager = null;
    private boolean isLoggedIn = false;

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    private String appTitle = "";

    static Scene registrationScene = null;
    static Scene loginScene = null;
    static Scene overviewScene = null;

    private ViewManager() {
        JSONConfigService config = new JSONConfigService("config.json");

        try {
            appTitle = config.get("appTitle");
        } catch (KeyNotFoundException e) {
            System.out.println("KeyNotFoundException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ViewManager getInstance() {
        if (viewManager == null)
            viewManager = new ViewManager();

        return viewManager;
    }

    public Stage getPrimaryStage() {
        return stage;
    }

    public void setPrimaryStage(Stage stage) {
        this.stage = stage;
    }

    private Scene loadSceneFromFile(String path) {
        try {
            Parent sceneRoot = FXMLLoader.load(getClass().getResource(path));
            return new Scene(sceneRoot);
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void display(View view) {
        switch (view) {
            case REGISTRATION:
                if (registrationScene == null) {
                    registrationScene = loadSceneFromFile("views/" + "Registration" + ".fxml");
                    registrationScene.getStylesheets().add(
                            getClass().getResource("views/" + "Main" + ".css").toExternalForm()
                    );
                }
                stage.setScene(registrationScene);
                stage.setTitle(appTitle + " - " + TranslationService.translate("views.registration.title"));
                break;

            case LOGIN:
                if (loginScene == null) {
                    loginScene = loadSceneFromFile("views/" + "Login" + ".fxml");
                    loginScene.getStylesheets().add(
                            getClass().getResource("views/" + "Main" + ".css").toExternalForm()
                    );
                }
                stage.setScene(loginScene);
                stage.setTitle(appTitle + " - " + TranslationService.translate("views.login.title"));
                break;

            case OVERVIEW:
                if (overviewScene == null) {
                    overviewScene = loadSceneFromFile("views/" + "Overview" + ".fxml");
                    overviewScene.getStylesheets().add(
                            getClass().getResource("views/" + "Main" + ".css").toExternalForm()
                    );
                }
                stage.setScene(overviewScene);
                stage.setTitle(appTitle + " - " + TranslationService.translate("views.overview.title"));

                break;
        }

        // not where it belongs, but prevents noticable window resize when switching scenes
        if (!stage.isShowing())
            stage.show();
    }
}
