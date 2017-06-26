package wizard.models;

public class Report {

    AnnualPlan annualPlan;

    private String department; // Abteilung: Informationstechnik Industrie
    private Profession profession; // Beruf: Fachinformatiker/in Anwendungsentwicklung
    private String teachingForm; // Unterrichtsform: Blockunterricht
    private String director; // Leiter: Herr Gottwald

    public Report(String department, Profession profession, String teachingForm, String director) {
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
