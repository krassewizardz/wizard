package wizard;

import javafx.application.Application;
import javafx.stage.Stage;
import wizard.models.View;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Class.forName("org.sqlite.JDBC");
        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setPrimaryStage(stage);
        viewManager.display(View.LOGIN);
    }
}