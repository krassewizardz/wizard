package wizard.repositories;

import org.sql2o.Connection;
import wizard.models.Profession;
import wizard.services.DBServiceProvider;

import java.util.List;

/**
 * Created by jansziegaud on 23.06.17.
 */

public class ProfessionSQLRepository {

    DBServiceProvider dbServiceProvider;

    public ProfessionSQLRepository(DBServiceProvider dbServiceProvider) {
        this.dbServiceProvider = dbServiceProvider;
    }

    public List<Profession> getAllProfessionsWithId() {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                            "SELECT %1s AS name, bid AS id, jahr as yearOfTraining " +
                            "FROM tbl_beruf " + "JOIN tbl_uformberuf on bid = id_beruf JOIN tbl_beruffach on id_uformberuf = ubid",

                    Constants.PROFESSION_NAME,
                    Constants.PROFESSION_ID,
                    Constants.PROFESSION_TABLENAME))
                    .executeAndFetch(Profession.class);
        }
    }

    public Profession getProfessionDetails() {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                    "SELECT "
            )).executeScalar(Profession.class);
        }
    }
}
