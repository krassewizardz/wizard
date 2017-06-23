package wizard.repositories;

import org.sql2o.Connection;
import wizard.models.Profession;
import wizard.services.DBServiceProvider;

import java.util.List;

/**
 * Created by jansziegaud on 23.06.17.
 */

public class ProfessionSQLRepository {

    private static final String TBLNAME_TBL_BERUF = "tbl_beruf";
    private static final String TBL_BERUF_BERUFNAME = "Berufname";
    private static final String TBL_BERUF_ID = "Bid";


    DBServiceProvider dbServiceProvider;

    public ProfessionSQLRepository(DBServiceProvider dbServiceProvider) {
        this.dbServiceProvider = dbServiceProvider;
    }

    List<Profession> getAllProfessionsWithId() {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format("SELECT %1s AS name, %2s AS id FROM %3s;",
                    TBL_BERUF_BERUFNAME,
                    TBL_BERUF_ID,
                    TBLNAME_TBL_BERUF)).executeAndFetch(Profession.class);
        }
    }
}
