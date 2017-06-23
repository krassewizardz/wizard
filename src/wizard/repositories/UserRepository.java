package wizard.repositories;

import wizard.models.User;
import wizard.services.DBServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class UserRepository implements Repository<User> {

    DBServiceProvider dbServiceProvider;

    public UserRepository(DBServiceProvider dbServiceProvider) {
        this.dbServiceProvider = dbServiceProvider;
    }

    @Override
    public void add(User item) {

    }

    @Override
    public void add(Iterable<User> items) {

    }

    @Override
    public void update(User item) {

    }

    @Override
    public void remove(User item) {

    }

    @Override
    public void remove(Specification specification) {

    }

    @Override
    public List<User> query(Specification specification) {
        return new ArrayList<>();
    }
}
