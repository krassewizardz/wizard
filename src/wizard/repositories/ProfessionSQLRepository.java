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

    List<Profession> getAllProfessionsWithId() {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                            "SELECT %1s AS name, %2s AS id " +
                            "FROM %3s" +
                            "JOIN tbl_uformberuf ON tbl_uformberuf.id_beruf = %2s;",
                    Constants.PROFESSION_NAME,
                    Constants.PROFESSION_ID,
                    Constants.PROFESSION_TABLENAME))
                    .executeAndFetch(Profession.class);
        }
    }

    Profession getProfessionDetails() {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                    "SELECT "
            )).executeScalar(Profession.class);
        }
    }
}
