package wizard.repositories;

import org.sql2o.Connection;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import wizard.models.Field;
import wizard.models.Subject;
import wizard.services.DBServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class SubjectSQLRepository {

    private DBServiceProvider dbServiceProvider;

    public SubjectSQLRepository(DBServiceProvider dbServiceProvider) {
        this.dbServiceProvider = dbServiceProvider;
    }

    public List<Subject> query() {
        try (Connection con = (Connection)dbServiceProvider.open()) {

        }
        final List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("test", new ArrayList<Field>()));
        return subjects;
    }
}
