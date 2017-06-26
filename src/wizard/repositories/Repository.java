package wizard.repositories;

import wizard.utility.QueryParam;

import java.util.List;

/**
 *  @author J. Sziegaud
 *  @author F. Engels
 *  TODO@all: description
 */
public interface Repository <T> {
    void add(T item);
    void add(Iterable<T> items);
    void update(T item);
    void remove(Integer id);
    void remove(T item);
    void flush();
    List<T> get();
    T get(Integer id);
    List<T> query(String query, QueryParam... params);
}
