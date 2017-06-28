package wizard.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import wizard.ViewManager;
import wizard.models.Configuration;
import wizard.models.Profession;
import wizard.models.Subject;
import wizard.models.User;
import wizard.repositories.ProfessionSQLRepository;
import wizard.repositories.SubjectSQLRepository;
import wizard.models.*;
import wizard.pdf.PdfGenerator;
import wizard.repositories.*;
import wizard.services.SQL2ODBServiceProvider;
import wizard.services.SQLiteAuthenthicationService;

import javax.xml.bind.annotation.XmlAnyAttribute;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Overview1Controller implements Initializable {

    private ViewManager viewManager = ViewManager.getInstance();
    private String selectedYear;
    private String selectedProfession;
    private Report report;
    private User currentUser;

    @FXML
    ComboBox yearComboBox, professionComboBox;
    @FXML
    Button backBtn, savePDFBtn, saveConfigurationBtn;
    @FXML
    Label titleLbl, professionLbl, yearLbl, configLbl, loadConfigLbl, configNameLbl;
    @FXML
    CheckBox scenarioCbx, outcomeCbx, competencesCbx, contentCbx, materialsCbx, commentsCbx, techniquesCbx, achievementsCbx;
    @FXML
    TextField configNameTxt;
    @FXML
    GridPane configGrid;
    @FXML
    ListView<String> configurationListView;
    @FXML
    TableView<Subject> tableView;
    @FXML
    TableColumn mainColumn;

    private ObservableList<String> yearObservableList;
    private ObservableList<String> professionObservableList;
    private ObservableList<Subject> tableViewItems;
    private ObservableList<String> configurationList;

    private List<Profession> professionList;

    SQL2ODBServiceProvider sql2ODBServiceProvider = new SQL2ODBServiceProvider();

    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        titleLbl.setText("Übersicht");
        yearLbl.setText("Ausbildungsjahr");
        professionLbl.setText("Ausbildungsberuf");
        configLbl.setText("Konfiguration");
        loadConfigLbl.setText("Konfiguration laden");
        configNameLbl.setText("Name");

        mainColumn.setText("Lernbereiche");

        scenarioCbx.setText("Szenario");
        outcomeCbx.setText("Outcome");
        competencesCbx.setText("Kompetenzen");
        contentCbx.setText("Inhalt");
        materialsCbx.setText("Materialien");
        commentsCbx.setText("Kommentare");
        techniquesCbx.setText("Techniken");
        achievementsCbx.setText("Achievements");
        configGrid.setVisible(viewManager.isLoggedIn());
        System.out.println(viewManager.isLoggedIn());

        backBtn.setText("Zurück");
        savePDFBtn.setText("Als PDF speichern");
        saveConfigurationBtn.setText("Konfiguration speichern");

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

        SQLiteAuthenthicationService sqLiteAuthenthicationService = SQLiteAuthenthicationService.getInstance();
        currentUser = sqLiteAuthenthicationService.getLoggedInUser();

        //TODO if logged in: load userconfig
        if(currentUser != null) {
            List<Configuration> userConfigurations = currentUser.getConfigurations();
            for(Configuration configuration : userConfigurations) {
                configurationList.add(configuration.getName());
            }
        }

        professionComboBox.setItems(FXCollections.observableArrayList(professionObservableList));
        yearComboBox.setItems(FXCollections.observableArrayList(yearObservableList));
        configurationListView.setItems(FXCollections.observableArrayList(configurationList));

        configurationListView.getSelectionModel().selectedItemProperty().addListener((
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        loadConfig(newValue);
                    }
                }
        ));
    }

    public void back() {
        viewManager.display(ViewManager.View.LOGIN);
    }

    public void savePDF() {
        DirectoryChooser chooser = new DirectoryChooser();
        Stage stage = new Stage(    );
        File defaultDirectory = new File("C:/");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(stage);
        UserRepository userRepository = UserRepository.getInstance();

        PdfGenerator generator = new PdfGenerator();
        try{
            generator.exportToFile(getReport(), selectedDirectory.toString(), new User("NAME", "USERNAME", "PASSWORD"));

        }catch(Exception ex) {

}
        System.out.println("Should save PDF with settings now");
        System.out.println("Get complete data and save as pdf");
    }

    public void chooseYear() {
        selectedYear = yearComboBox.getSelectionModel().getSelectedItem().toString();
        if (selectedProfession != null) {
            getSubject();
        }
    }

    public void chooseProfession() {
        selectedProfession = professionComboBox.getSelectionModel().getSelectedItem().toString();
        if (selectedYear != null) {
            getSubject();
        }
    }

    public Report getReport(){
        SubjectSQLRepository subjectSQLRepository = new SubjectSQLRepository(sql2ODBServiceProvider);
        ReportSQLRepository reportSQLRepository = new ReportSQLRepository(sql2ODBServiceProvider);
        ProfessionSQLRepository professionSQLRepository = new ProfessionSQLRepository(sql2ODBServiceProvider);
        FieldSQLRepository fieldSQLRepository = new FieldSQLRepository(sql2ODBServiceProvider);
        SituationSQLRepository situationSQLRepository = new SituationSQLRepository(sql2ODBServiceProvider);

        List<Subject> subjects;
        List<Field> fields ;
        List<Situation> situations ;


        for(Profession profession : professionSQLRepository.getAllProfessionsWithId()){
            if(profession.getName().equals(selectedProfession)) {
                subjects = subjectSQLRepository.getAllSubjectsForProfession(profession);
                for(Subject subject : subjects){
                    fields = fieldSQLRepository.getAllFieldsForSubject(subject);
                    for(Field field : fields){
                        situations = situationSQLRepository.getAllSitutationsForField(field);
                        /*for(Situation situation : situations){
                            situation.setAchievements(situationSQLRepository.getAllAchievments(situation));
                            situation.setTechniques(situationSQLRepository.getAllTechniques(situation));
                        }
                        */
                        field.setSituations(situations);
                    }
                    subject.setFields(fields);
                }
               // profession.setYearOfTraining((Integer.parseInt(selectedYear)));
               // profession.setSubjects(subjectSQLRepository.getAllSubjectsForProfession(profession));
                return report = reportSQLRepository.get(profession, Integer.parseInt(selectedYear));
            }
        }
        return report;
    }

    public void getSubject() {
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
        Configuration configuration = new Configuration(
                configNameTxt.getText(),
                currentUser.getId(),
                scenarioCbx.isSelected(),
                outcomeCbx.isSelected(),
                competencesCbx.isSelected(),
                contentCbx.isSelected(),
                materialsCbx.isSelected(),
                commentsCbx.isSelected(),
                techniquesCbx.isSelected(),
                achievementsCbx.isSelected()
        );


        //TODO service to save config for user
        //TODO do not save config when name already exists
        //TODO reload config
    }

    private void loadConfig(String configname) {
        List<Configuration> configurations = currentUser.getConfigurations();
        for(Configuration configuration : configurations) {
            if(configuration.getName().equals(configname)) {
                scenarioCbx.setSelected(configuration.isScenario());
                outcomeCbx.setSelected(configuration.isOutcome());
                competencesCbx.setSelected(configuration.isCompetence());
                contentCbx.setSelected(configuration.isContent());
                materialsCbx.setSelected(configuration.isMaterials());
                commentsCbx.setSelected(configuration.isComments());
                techniquesCbx.setSelected(configuration.isTechniques());
                achievementsCbx.setSelected(configuration.isAchievements());
                break;
            }
        }
    }

}
