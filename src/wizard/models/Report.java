package wizard.models;

public class Report {

    AnnualPlan annualPlan;

    private int yearOfTraining;
    private String department; // Informationstechnik Industrie
    private Profession profession; // Fachinformatiker/in Anwendungsentwicklung Ausbildungsjahr: 1
    private String teachingForm; // Blockunterricht
    private String director; // Herr Gottwald

    public Report(int yearOfTraining, String department, Profession profession, String teachingForm, String director) {
        this.yearOfTraining = yearOfTraining;
        this.department = department;
        this.profession = profession;
        this.teachingForm = teachingForm;
        this.director = director;
    }

    public AnnualPlan getAnnualPlan() {
        return annualPlan;
    }

    public void setAnnualPlan(AnnualPlan annualPlan) {
        this.annualPlan = annualPlan;
    }
}
