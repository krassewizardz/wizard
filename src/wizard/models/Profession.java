package wizard.models;

import java.util.List;

/**
 * Created by jansziegaud on 23.06.17.
 */
public class Profession {

    private Integer id; // Id: 123
    private String name; // Bezeichnung: Fachinformatiker bliblablupp
    private int yearOfTraining; // Das Ausbildungsjahr
    private List<Subject> subjects;

    public Profession(Integer id, String name, int yearOfTraining) {
        this.id = id;
        this.name = name;
        this.yearOfTraining = yearOfTraining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getYearOfTraining() {
        return yearOfTraining;
    }

    public void setYearOfTraining(int yearOfTraining) {
        this.yearOfTraining = yearOfTraining;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
