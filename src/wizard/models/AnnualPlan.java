package wizard.models;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class AnnualPlan {
    List<Subject> subjects;

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public AnnualPlan(List<Subject> subjects) {
        this.subjects = subjects;
    }
}
