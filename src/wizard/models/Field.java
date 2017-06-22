package wizard.models;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class Field { // Lernfelder

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<Situation> getSituations() {
        return situations;
    }

    public void setSituations(List<Situation> situations) {
        this.situations = situations;
    }

    public Field(String id, String name, int duration, List<Situation> situations) {

        this.id = id;
        this.name = name;
        this.duration = duration;
        this.situations = situations;
    }

    private String id;
    private String name;
    private int duration;
    List<Situation> situations;
}
