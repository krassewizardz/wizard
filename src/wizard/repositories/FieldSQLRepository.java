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
            return con.createQuery(String.format(
                    "select distinct lfid as id, tbl_lernfeld.Bezeichnung as name, tbl_lernfeld.lfdauer as duration\n" +
                    "from tbl_lernfeld\n" +
                    "join tbl_beruffach on bfid = id_beruffach\n" +
                    "join tbl_uformberuf on id_uform = id_uformberuf\n" +
                    "join tbl_fach on id_fach = fid\n" +
                    "where fid = :subject_id;"))
                    .addParameter("subject_id", s.getId())
                    .executeAndFetch(Field.class);
        }
    }
}
