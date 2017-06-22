package wizard.services;

import java.nio.file.Path;


public interface ConfigServiceProvider {
    String get(String k);
    void set(String k, String v);
    void set(String k, Integer v);
    void set(String k, Float v);
    void loadFromFile(Path s);
    void saveToFile(Path d);
}
