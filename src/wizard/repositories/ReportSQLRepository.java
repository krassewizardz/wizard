package wizard.repositories;

import org.sql2o.Connection;
import wizard.models.*;
import wizard.models.Situation;
import wizard.models.Report;
import wizard.services.DBServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class ReportSQLRepository {

    DBServiceProvider dbService;

    ReportSQLRepository(DBServiceProvider dbService) {
        this.dbService = dbService;
    }

    List<Situation> getDetails() {
        return new ArrayList<>();
    }

    public Report get(Profession profession, int yearOfTraining) {

        final String getMainInformation =
                "select abteilungsname as department, lehrername as director, uformname as teachingForm " +
                "from tbl_beruf " +
                "join tbl_abteilung on aid = id_abteilung " +
                "join tbl_lehrer on id_bleitung = lid " +
                "join tbl_uformberuf on id_beruf = bid " +
                "join tbl_uform on id_uform = uid " +
                "join tbl_beruffach on id_uformberuf = ubid " +
                "join tbl_fach on fid = id_fach " +
                "where jahr = :year_of_training "+
                "and bid = :profession_id;";

        try (Connection con = (Connection)dbService.open()) {
            return con.createQuery(getMainInformation)
                    .addParameter("profession_id", profession.getId())
                    .addParameter("year_of_training", yearOfTraining)
                    .executeScalar(Report.class);
        }
    }
}
