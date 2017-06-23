package wizard.repositories;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import wizard.models.Field;
import wizard.models.Subject;
import wizard.repositories.specifications.SQLSpecification;
import wizard.services.DBServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class SubjectSQLRepository implements Repository<Subject> {

    private DBServiceProvider dbServiceProvider;

    public SubjectSQLRepository(DBServiceProvider dbServiceProvider) {
        this.dbServiceProvider = dbServiceProvider;
    }

    @Override
    public void add(Subject item) {
        throw new NotImplementedException();
    }

    @Override
    public void add(Iterable<Subject> items) {
        throw new NotImplementedException();
    }

    @Override
    public void update(Subject item) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(Subject item) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(Specification specification) {
        throw new NotImplementedException();
    }

    @Override
    public List<Subject> query(Specification specification) {
        final SQLSpecification sqlSpecification = (SQLSpecification) specification;
        final List<Subject> subjects = new ArrayList<>();
        subjects.add(new Subject("test", new ArrayList<Field>()));
        return subjects;
    }
}
