package wizard.models;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class Situation {
    private String id;
    private String name;
    private int duration;
    private int start;
    private int end;

    public Situation(String id, String name, int duration, int start, int end) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.start = start;
        this.end = end;
    }

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
}
