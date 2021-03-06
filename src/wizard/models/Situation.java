package wizard.models;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class Situation {
    private int id;
    private String name;
    private int duration;
    private int start;
    private int end;
    private String scenario; // Auftrag vom Firmenchef an die Fachabteilung ... wall of text
    private String outcome; // Handlungsprodukt/Lernergebnis:
    private String competences; // Die Schüler planen die Aufgaben und Reihenfolge eines Netzwerkes. ... wall of text
    private String content; // Erstellen einer Aufgabenliste zur Einrichtung des ... wall of text
    private String materials; // Unterrichtsmaterialien;
    private String comments; // OrganisatorischeHinweise;
    private List<Technique> techniques; // LernUndArbeitstechniken;
    private List<Achievement> achievements; // Achievement;

    public Situation(int id, String name, int duration, int start, int end) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getScenario() {
        return scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaterials() {
        return materials;
    }

    public void setMaterials(String materials) {
        this.materials = materials;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Technique> getTechniques() {
        return techniques;
    }

    public void setTechniques(List<Technique> techniques) {
        this.techniques = techniques;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    public void setAchievements(List<Achievement> achievement) {
        this.achievements = achievement;
    }
}
