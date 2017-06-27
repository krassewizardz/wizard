package wizard.models;

/**
 * Created by jansziegaud on 27.06.17.
 */
public class Achievement {
    int id;
    String name;

    public Achievement(int id, String name) {
        this.id = id;
        this.name = name;
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
}
