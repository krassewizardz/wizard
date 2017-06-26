package wizard.repositories;

import org.sql2o.Connection;
import wizard.models.Field;
import wizard.models.Situation;
import wizard.services.DBServiceProvider;

import java.util.List;

/**
 * Created by jansziegaud on 23.06.17.
 */
public class SituationSQLRepository {
    private DBServiceProvider dbServiceProvider;

    public SituationSQLRepository(DBServiceProvider dbServiceProvider) {
        this.dbServiceProvider = dbServiceProvider;
    }

    List<Situation> getAllSitutationsForField(Field f) {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                                "SELECT * FROM %1s " +
                                "JOIN %2s ON %1s.LF_NR = %2s.%3s " +
                                "WHERE LFID = :field_id;",
                    Constants.SITUATION_TABLENAME,
                    Constants.FIELD_TABLENAME,
                    Constants.FIELD_ID,
                    f.getId())).executeAndFetch(Situation.class);
        }
    }
}
