package wizard.repositories;

import org.sql2o.Connection;
import wizard.models.Field;
import wizard.models.Achievement;
import wizard.models.Situation;
import wizard.models.Technique;
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

    public List<Situation> getAllSitutationsForField(Field f) {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                    "select distinct lsid as id, name, Szenario as scenario," +
                    "udauer as duration, von as start, bis as end, handlungsprodukt as outcome," +
                    "kompetenzen as competences, inhalte as content, umaterial as materials\n" +
                    "from tbl_lernsituation\n" +
                    "join tbl_lernfeld on id_lernfeld = lfid\n" +
                    "where lfid = :field_id;",
                    Constants.SITUATION_TABLENAME,
                    Constants.FIELD_TABLENAME,
                    Constants.FIELD_ID))
                    .addParameter("field_id", f.getId())
                    .executeAndFetch(Situation.class);
        }
    }
    public List<Achievement> getAllAchievments(Situation s) {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                    "select distinct lnid as id, art as name\n" +
                    "from tbl_leistungsnachweis\n" +
                    "join tbl_lernsituationleistungsnachweis on ID_Leistungsnachweis = lnid\n" +
                    "join tbl_lernsituation on ID_Lernsituation = lsid\n" +
                    "where lsid = :situation_id"))
                    .addParameter("situation_id", s.getId())
                    .executeAndFetch(Achievement.class);
        }
    }

    public List<Technique> getAllTechniques(Situation s) {
        try (Connection con = (Connection)dbServiceProvider.open()) {
            return con.createQuery(String.format(
                    "select distinct technik, name from tbl_lerntechnik\n" +
                    "join tbl_lernsituationlerntechnik on latid = ID_Lerntechnik\n" +
                    "join tbl_lernsituation on ID_Lernsituation = lsid\n" +
                    "where lsid = :situation_id;"))
                    .addParameter("sitation_id", s.getId())
                    .executeAndFetch(Technique.class);
        }
    }
}
