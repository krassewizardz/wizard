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
import wizard.services.TranslationService;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OverviewController implements Initializable {

    private ViewManager viewManager = ViewManager.getInstance();
    private UserRepository userRepository = UserRepository.getInstance();
    private SQLiteAuthenthicationService sqLiteAuthenthicationService = SQLiteAuthenthicationService.getInstance();
    private SQL2ODBServiceProvider sql2ODBServiceProvider = new SQL2ODBServiceProvider();

    private String selectedYear;
    private String selectedProfession;
    private User currentUser;
    private List<Profession> professionList;

    private ObservableList<String> yearObservableList;
    private ObservableList<String> professionObservableList;
    private ObservableList<Subject> tableViewItems;
    private ObservableList<String> configurationList;

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


    @Override
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        titleLbl.setText(TranslationService.translate("views.overview.titleLbl"));
        yearLbl.setText(TranslationService.translate("views.overview.yearLbl"));
        professionLbl.setText(TranslationService.translate("views.overview.professionLbl"));
        configLbl.setText(TranslationService.translate("views.overview.configLbl"));
        loadConfigLbl.setText(TranslationService.translate("views.overview.loadConfigLbl"));
        configNameLbl.setText(TranslationService.translate("views.overview.configNameLbl"));
        mainColumn.setText(TranslationService.translate("views.overview.mainColumn"));
        scenarioCbx.setText(TranslationService.translate("views.overview.scenarioCbx"));
        outcomeCbx.setText(TranslationService.translate("views.overview.outcomeCbx"));
        competencesCbx.setText(TranslationService.translate("views.overview.competencesCbx"));
        contentCbx.setText(TranslationService.translate("views.overview.contentCbx"));
        materialsCbx.setText(TranslationService.translate("views.overview.materialsCbx"));
        commentsCbx.setText(TranslationService.translate("views.overview.commentsCbx"));
        techniquesCbx.setText(TranslationService.translate("views.overview.techniquesCbx"));
        achievementsCbx.setText(TranslationService.translate("views.overview.achievementsCbx"));
        backBtn.setText(TranslationService.translate("views.overview.backBtn"));
        savePDFBtn.setText(TranslationService.translate("views.overview.savePDFBtn"));
        saveConfigurationBtn.setText(TranslationService.translate("views.overview.saveConfigurationBtn"));

        configGrid.setVisible(SQLiteAuthenthicationService.getInstance().hasValidLogin());

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

        currentUser = sqLiteAuthenthicationService.getLoggedInUser();

        //TODO funktioniert erst, wenn userservice ordentlich läuft
        /*if(viewManager.isLoggedIn()) {
            List<Configuration> userConfigurations = currentUser.getConfigurations();
            for(Configuration configuration : userConfigurations) {
                configurationList.add(configuration.getName());
            }
        }*/

        professionComboBox.setItems(FXCollections.observableArrayList(professionObservableList));
        yearComboBox.setItems(FXCollections.observableArrayList(yearObservableList));
        configurationListView.setItems(FXCollections.observableArrayList(configurationList));

        configurationListView.getSelectionModel().selectedItemProperty().addListener((
                (observable, oldValue, newValue) -> loadConfig(newValue)
        ));
    }

    public void back() {
        viewManager.display(View.LOGIN);
    }

    public void savePDF() {
        Configuration configuration = new Configuration();
        if(SQLiteAuthenthicationService.getInstance().hasValidLogin()) {
            configuration.setScenario(scenarioCbx.isSelected());
            configuration.setOutcome(outcomeCbx.isSelected());
            configuration.setCompetence(competencesCbx.isSelected());
            configuration.setContent(contentCbx.isSelected());
            configuration.setMaterials(materialsCbx.isSelected());
            configuration.setComments(commentsCbx.isSelected());
            configuration.setTechniques(techniquesCbx.isSelected());
            configuration.setAchievements(achievementsCbx.isSelected());
        }
        DirectoryChooser chooser = new DirectoryChooser();
        Stage stage = new Stage(    );
        File defaultDirectory = new File("C:/");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(stage);

        PdfGenerator generator = new PdfGenerator();
        try{
            generator.exportToFile(getReport(), selectedDirectory.toString(), new User("NAME", "USERNAME", "PASSWORD"));

        }catch(Exception ex) {
            ex.printStackTrace();
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
        professionList = professionSQLRepository.getAllProfessionsWithId();


        for(Profession profession : professionList){
            if(profession.getName().equals(selectedProfession)) {
                subjects = subjectSQLRepository.getAllSubjectsForProfession(profession);
                profession.setSubjects(subjects);
                for(Subject subject : subjects){
                    fields = fieldSQLRepository.getAllFieldsForSubject(subject);
                    subject.setFields(fields);
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
                Report report =  reportSQLRepository.get(profession, Integer.parseInt(selectedYear));
                report.setProfession(profession);
                return report;
            }
        }
        return null;
    }

    public void getSubject() {
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
        List<Configuration> configurations = currentUser.getConfigurations();
        for(Configuration configuration : configurations) {
            if(configuration.getName().equals(configNameTxt.getText())) {
                return;
            }
        }

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

        System.out.println("Should save configuration to user");
        System.out.println("Should load configuration again");

        //TODO service to save config for user
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
