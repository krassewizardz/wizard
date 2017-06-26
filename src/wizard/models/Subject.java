package wizard.models;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class Subject { // Lernbereich
    private int id;
    private String name;
    List<Field> fields;

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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public Subject(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject(int id, String name, List<Field> fields) {
        this(id, name);
        this.fields = fields;
    }
}
