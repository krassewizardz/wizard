package wizard.models;

/**
 * Created by jansziegaud on 23.06.17.
 */
public class Profession {

    private Integer id; // Id: 123
    private String name; // Bezeichnung: Fachinformatiker bliblablupp

    public Profession(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
