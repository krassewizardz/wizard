package wizard.repositories;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import wizard.models.AnnualPlan;
import wizard.models.Situation;
import wizard.models.Report;
import wizard.services.DBServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class ReportRepository {

    DBServiceProvider dbService;

    ReportRepository(DBServiceProvider dbService) {
        this.dbService = dbService;
    }

    List<Situation> getDetails() {
        return new ArrayList<>();
    }

    public Report get(String profession, int yearOfTraining) {
        final Report rep = new Report(
                yearOfTraining,
                "testdep",
                profession,
                "testform",
                "testdir"
        );
        rep.setAnnualPlan(new AnnualPlan());
        return rep;
    }

    public Report getDetails(Report rep) {
        rep.setAnnualPlan(new AnnualPlan());
        return rep;
    }
}
