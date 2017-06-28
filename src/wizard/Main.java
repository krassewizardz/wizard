package wizard;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ViewManager vm = ViewManager.getInstance();
        vm.setPrimaryStage(stage);
        vm.display(ViewManager.View.LOGIN);
    }
}