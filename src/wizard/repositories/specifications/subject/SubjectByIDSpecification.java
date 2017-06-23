package wizard.repositories.specifications.subject;

import wizard.repositories.specifications.SQLSpecification;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class SubjectByIDSpecification implements SQLSpecification {

    private final int id;

    public SubjectByIDSpecification(int id) {
        this.id = id;
    }

    @Override
    public String toSQLQuery() {
        return String.format("SELECT * FROM %1$s WHERE id = `%2$s` DESC;",
                "",
                id);
    }
}
