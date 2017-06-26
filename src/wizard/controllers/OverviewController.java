package wizard.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import wizard.ViewManager;

import java.net.URL;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {

    private ViewManager viewManager = ViewManager.getInstance();

    @FXML
    ComboBox yearChoiceBox, professionChoiceBox;
    @FXML
    Button backBtn, savePDFBtn;
    @FXML
    Label titleLbl, professionLbl, yearLbl, configLbl;
    @FXML
    CheckBox firstCbx, secondCbx, thirdCbx, fourthCbx, fifthCbx, sixthCbx;

    @FXML
    GridPane rightGrid;

    private ObservableList<String> years;
    private ObservableList<String> jobs;

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        titleLbl.setText("Übersicht");
        professionLbl.setText("Ausbildungsberuf");
        yearLbl.setText("Ausbildungsjahr");
        configLbl.setText("Konfiguration");

        firstCbx.setText("First");
        secondCbx.setText("Second");
        thirdCbx.setText("Third");
        fourthCbx.setText("Fourth");
        fifthCbx.setText("Fifth");
        sixthCbx.setText("Sixth");
        rightGrid.setVisible(viewManager.isLoggedIn());

        backBtn.setText("Zurück");
        savePDFBtn.setText("Als PDF speichern");

        jobs = FXCollections.observableArrayList();
        years = FXCollections.observableArrayList();

        //TODO jobs aus DB
        jobs.add("Fachinformatiker Anwendungsentwicklung");
        jobs.add("Fachinformatiker Systemintegration");
        jobs.add("Systemelektroniker");

        //TODO ausbildungsjahre aus db
        years.add("1");
        years.add("2");
        years.add("3");

        professionChoiceBox.setItems(FXCollections.observableArrayList(jobs)
        );

        yearChoiceBox.setItems(FXCollections.observableArrayList(years)
        );

        System.out.println(viewManager.isLoggedIn());
    }

    public void back() {
        viewManager.gotoLogin();
    }

    public void savePDF() {
        System.out.println("Should save PDF with settings now");
    }
}
