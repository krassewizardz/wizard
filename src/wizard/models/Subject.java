package wizard.models;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class Subject { // Lernbereich
    private String name;
    List<Field> fields;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Subject(String name, List<Field> fields) {

        this.name = name;
        this.fields = fields;
    }
}
