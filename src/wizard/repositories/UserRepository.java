package wizard.repositories;

import wizard.models.User;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public class UserRepository implements Repository<User> {
    @Override
    public User getById(String id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void save(User entity) {

    }

    @Override
    public void update(User entity) {

    }
}
