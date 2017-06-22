package wizard.repositories;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public interface Repository <T> {
    T getById(String id);
    List<T> getAll();
    void save(T entity);
    void update(T entity);
}
