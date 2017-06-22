package wizard.repositories;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import wizard.models.*;
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
        /*
            MOCK
         */

        final Situation situation = new Situation("1", "situationtest", 40, 1, 12);
        final List<Situation> situations = new ArrayList<>();
        situations.add(situation);

        final Field field = new Field("1", "fieldtest", 1, situations);
        final List<Field> fields = new ArrayList<>();
        fields.add(field);

        final Subject subject = new Subject("subtest", fields);
        final List<Subject> subjects = new ArrayList<>();
        subjects.add(subject);

        final AnnualPlan annualPlan = new AnnualPlan(subjects);

        final Report rep = new Report(
                yearOfTraining,
                "testdep",
                profession,
                "testform",
                "testdir"
        );
        rep.setAnnualPlan(annualPlan);
        return rep;
    }

    public Report getDetails(Report rep) {
        return rep;
    }
}
