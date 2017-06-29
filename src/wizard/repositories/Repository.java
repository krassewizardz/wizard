package wizard.repositories;



import java.util.List;


/**
 *  @author J. Sziegaud
 *  @author F. Engels
 *  TODO@all: add documentation
 */
public interface Repository <T> {
    void add(T item) throws Exception;
    void add(Iterable<T> items) throws Exception;
    void update(T item) throws Exception;
    void remove(Integer id) throws Exception;
    void remove(T item) throws Exception;
    void flush() throws Exception;
    List<T> get() throws Exception;
    T get(Integer id) throws Exception;
}
