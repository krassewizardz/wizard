package wizard.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import wizard.ViewManager;
import wizard.models.Profession;
import wizard.models.Subject;
import wizard.repositories.ProfessionSQLRepository;
import wizard.repositories.SubjectSQLRepository;
import wizard.services.SQL2ODBServiceProvider;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Overview1Controller implements Initializable {

    private ViewManager viewManager = ViewManager.getInstance();
    private String selectedYear;
    private String selectedProfession;

    @FXML
    ComboBox yearComboBox, professionComboBox;
    @FXML
    Button backBtn, savePDFBtn, saveConfigurationBtn;
    @FXML
    Label titleLbl, professionLbl, yearLbl, configLbl;
    @FXML
    CheckBox firstCbx, secondCbx, thirdCbx, fourthCbx, fifthCbx, sixthCbx;
    @FXML
    TextField configurationTxt;
    @FXML
    GridPane rightGrid;
    @FXML
    ListView configurationListView;
    @FXML
    TableView<Subject> tableView;

    private ObservableList<String> yearObservableList;
    private ObservableList<String> professionObservableList;
    private ObservableList<Subject> tableViewItems;
    private ObservableList<String> configurationList;

    private List<Profession> professionList;

    SQL2ODBServiceProvider sql2ODBServiceProvider = new SQL2ODBServiceProvider();

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

        professionObservableList = FXCollections.observableArrayList();
        yearObservableList = FXCollections.observableArrayList();
        configurationList = FXCollections.observableArrayList();

        ProfessionSQLRepository professionSQLRepository = new ProfessionSQLRepository(sql2ODBServiceProvider);

        professionList = professionSQLRepository.getAllProfessionsWithId();

        for (Profession prof : professionList) {
            if(!professionObservableList.contains(prof.getName())) {
                System.out.println(prof.toString());
                professionObservableList.add(prof.getName());
            }
            if(!yearObservableList.contains(String.valueOf(prof.getYearOfTraining()))) {
                yearObservableList.add(String.valueOf(prof.getYearOfTraining()));
            }
        }

        //TODO if logged in: load userconfig
        if(true) {
            configurationList.add("lala");
            configurationList.add("lala");
            configurationList.add("lala");
            configurationList.add("lala");
        }

        professionComboBox.setItems(FXCollections.observableArrayList(professionObservableList));
        yearComboBox.setItems(FXCollections.observableArrayList(yearObservableList));
        configurationListView.setItems(FXCollections.observableArrayList(configurationList));
    }

    public void back() {
        viewManager.gotoLogin();
    }

    public void savePDF() {
        System.out.println("Should save PDF with settings now");
    }

    public void chooseYear() {
        selectedYear = yearComboBox.getSelectionModel().getSelectedItem().toString();
        if (selectedProfession != null) {
            getReport();
        }
    }

    public void chooseProfession() {
        selectedProfession = professionComboBox.getSelectionModel().getSelectedItem().toString();
        if (selectedYear != null) {
            getReport();
        }
    }

    public void getReport() {
        System.out.println("get called");
        String id;
        SubjectSQLRepository subjectSQLRepository = new SubjectSQLRepository(sql2ODBServiceProvider);
        List<Subject> subjectList = new ArrayList<>();

        for (Profession profession : professionList) {
            if (profession.getName().equals(selectedProfession)) {
                profession.setYearOfTraining(Integer.parseInt(selectedYear));
                System.out.println(profession.toString());
                subjectList = subjectSQLRepository.getAllSubjectsForProfession(profession);
                break;
            }
        }

        tableViewItems = tableView.getItems();
        tableViewItems.clear();

        for(Subject subject : subjectList) {
            System.out.println(subject.toString());
            tableViewItems.add(subject);
        }
    }

    public void saveConfiguration() {
        System.out.println("saveconfig");
        String configName = configurationTxt.getText();
        boolean first = firstCbx.isSelected();
        boolean second = secondCbx.isSelected();
        boolean third = thirdCbx.isSelected();
        boolean fourth = fourthCbx.isSelected();
        boolean fifth = fifthCbx.isSelected();
        boolean sixth = sixthCbx.isSelected();
        System.out.println(configName);

        //TODO service to save config for user
        //TODO reload config
    }

}
