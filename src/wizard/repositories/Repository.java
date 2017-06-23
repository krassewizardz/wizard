package wizard.repositories;

import java.util.List;

/**
 * Created by jansziegaud on 22.06.17.
 */
public interface Repository <T> {
    void add(T item);
    void add(Iterable<T> items);
    void update(T item);
    void remove(T item);
    void remove();
    T get();
    List<T> query();
}
