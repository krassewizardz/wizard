package wizard.services;

public interface DBServiceProvider<T> {
    T open();
}
