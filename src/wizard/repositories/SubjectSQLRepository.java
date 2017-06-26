package wizard.repositories;

import org.sql2o.Connection;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import wizard.models.Field;
import wizard.models.Profession;
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

    public List<Subject> getAllSubjectsForProfession(Profession p) {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                    "select bezeichnung as name, fid as id " +
                    "from tbl_fach " +
                    "join tbl_beruffach on fid = id_fach " +
                    "join tbl_uformberuf on id_uformberuf = ubid " +
                    "join tbl_beruf on id_beruf = bid " +
                    "where bid = :profession_id" +
                    "and jahr = :year_of_training;"
                    ))
                    .addParameter("profession_id", p.getId())
                    .addParameter("year_of_training", p.getYearOfTraining())
                    .executeAndFetch(Subject.class);
        }
    }
}
