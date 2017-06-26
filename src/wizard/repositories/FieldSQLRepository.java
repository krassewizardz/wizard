package wizard.repositories;

import org.sql2o.Connection;
import wizard.models.Field;
import wizard.models.Subject;
import wizard.services.DBServiceProvider;

import java.util.List;

/**
 * Created by jansziegaud on 26.06.17.
 */
public class FieldSQLRepository {
    private DBServiceProvider dbServiceProvider;

    public FieldSQLRepository(DBServiceProvider dbServiceProvider) {
        this.dbServiceProvider = dbServiceProvider;
    }

    public List<Field> getAllFieldsForSubject(Subject s) {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format("" +
                    "SELECT * FROM %1s" +
                    "WHERE"))
                    .executeAndFetch(Field.class);
        }
    }
}
