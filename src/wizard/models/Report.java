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

    public int getYearOfTraining() {
        return yearOfTraining;
    }

    public void setYearOfTraining(int yearOfTraining) {
        this.yearOfTraining = yearOfTraining;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public String getTeachingForm() {
        return teachingForm;
    }

    public void setTeachingForm(String teachingForm) {
        this.teachingForm = teachingForm;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
