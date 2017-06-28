package wizard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;


/**
 *  @author F. Prinz
 *  @author F. Engels
 *  TODO@all: add documentation
 */
public class ViewManager {

    private Stage stage;
    private static ViewManager instance = null;
    private boolean isLoggedIn = false;

    public enum View {
        REGISTRATION,
        LOGIN,
        OVERVIEW,
        MAIN
    }

    static Scene sRegistration = null;
    static Scene sLogin = null;
    static Scene sOverview = null;
    static Scene sMain = null;

    private ViewManager() {}

    public static ViewManager getInstance() {
        if (instance == null)
            instance = new ViewManager();

        return instance;
    }

    public Stage GetPrimaryStage() {
        return stage;
    }

    public void setPrimaryStage(Stage s) {
        stage = s;
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

                if (sRegistration == null) {
                    sRegistration = loadSceneFromFile("views/" + "Registration" + ".fxml");
                    sRegistration.getStylesheets().add(
                            getClass().getResource("views/" + "Main" + ".css").toExternalForm()
                    );
                }

                stage.setScene(sRegistration);
                stage.setTitle("Registration");

                break;
            case LOGIN:

                if (sLogin == null) {
                    sLogin = loadSceneFromFile("views/" + "Login" + ".fxml");
                    sLogin.getStylesheets().add(
                            getClass().getResource("views/" + "Main" + ".css").toExternalForm()
                    );
                }

                stage.setScene(sLogin);
                stage.setTitle("Login");

                break;
            case OVERVIEW:

                if (sOverview == null) {
                    sOverview = loadSceneFromFile("views/" + "Overview1" + ".fxml");
                    sOverview.getStylesheets().add(
                            getClass().getResource("views/" + "Main" + ".css").toExternalForm()
                    );
                }

                stage.setScene(sOverview);
                stage.setTitle("Overview");

                break;
            case MAIN:

                if (sMain == null) {
                    sMain = loadSceneFromFile("views/" + "Main" + ".fxml");
                    sMain.getStylesheets().add(
                            getClass().getResource("views/" + "Main" + ".css").toExternalForm()
                    );
                }

                stage.setScene(sMain);
                stage.setTitle("Main");

                break;
        }

        // not where it belongs, but prevents noticable window resize when switching scenes
        if (!stage.isShowing())
            stage.show();
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }
}
