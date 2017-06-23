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

        final String getPflichtInformation =
                "SELECT department."+Constants.DEPARTMENT_NAME +", t3."+ Constants.TBL_LEHRER_LEHRERNAME+", "
                + "t3." + Constants.TBL_LEHRER_GESCHLECHT+", t4."+ Constants.TBL_UFORMBERUF_UFBJAHR+", t4."+ Constants.TBL_UFORMBERUF_UWOCHEN+", t5."+Constants.TBL_UFORM_UFORMNAME+" "
                + "FROM `"+ Constants.PROFESSION_TABLENAME
                + "` AS t1 JOIN `"+ Constants.DEPARTMENT_TABLENAME +"` AS department ON t1."+Constants.TBL_BERUF_ID_ABTEILUNG+"=t2."+ Constants.TBL_ABTEILUNG_ID
                + " JOIN `"+ Constants.TBLNAME_TBL_LEHRER +"` AS t3 ON t1."+Constants.TBL_BERUF_ID_BLeitung+"=t3."+Constants.TBL_LEHRER_LID
                + " JOIN `"+ Constants.TBLNAME_TBL_UFORMBERUF +"` AS t4 ON t1."+ Constants.PROFESSION_ID
                + " JOIN `"+ Constants.TEACHINGFORM_TABLENAME +"` AS t5 ON t4."+ Constants.TBL_UFORMBERUF_ID_UFORM+"=t5."+Constants.TEACHINGFORM_ID + " "
                + "WHERE " + Constants.PROFESSION_TABLENAME + "." + Constants.PROFESSION_ID + "= :profession_id "
                + "AND " + Constants.TBLNAME_TBL_UFORMBERUF + "." + Constants.TBL_UFORMBERUF_UFBJAHR + "= :year_of_training;";

        try (Connection con = (Connection)dbService.open()) {
            return con.createQuery(getPflichtInformation)
                    .addParameter("profession_id", profession.getId())
                    .addParameter("year_of_training", yearOfTraining)
                    .executeScalar(Report.class);
        }
    }

    public Report getDetails(Report rep) {
        return rep;
    }
}
