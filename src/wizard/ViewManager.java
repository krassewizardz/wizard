package wizard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by fpr on 22.06.2017.
 */
public class ViewManager extends Application {

    private Stage stage;
    private static ViewManager viewManager = null;
    private boolean isLoggedIn = false;

    private static final String REGISTRATION = "Registration";
    private static final String LOGIN = "Login";
    private static final String OVERVIEW = "Overview";

    public ViewManager() {
        viewManager = this;
    }

    public static ViewManager getInstance() {
        return viewManager;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setisLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        gotoLogin();
        primaryStage.show();
    }

    public void goToOverview() {
        try {
            replaceSceneContent(OVERVIEW);
        } catch (Exception ex) {
            System.out.println("could not go to overview");
            ex.printStackTrace();
        }
    }

    public void gotoLogin() {
        try {
            setisLoggedIn(false);
            replaceSceneContent(LOGIN);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("could not go to login");
        }
    }

    public void gotoRegister() {
        try {
            replaceSceneContent(REGISTRATION);
        } catch (Exception ex) {
            System.out.println("could not go to register");
        }
    }

    private Parent replaceSceneContent(String view) throws Exception {
        Parent page = (Parent) FXMLLoader.load(getClass().getResource("views/" + view + ".fxml"), null, new JavaFXBuilderFactory());
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(page);
            scene.getStylesheets().add(getClass().getResource("views/" + view + ".css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Fancy Title");
        } else {
            stage.getScene().setRoot(page);
        }
        stage.sizeToScene();
        return page;
    }
}
