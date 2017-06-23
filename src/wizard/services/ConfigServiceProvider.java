package wizard.services;


public interface ConfigServiceProvider {
    String get(String k);
    void set(String k, Boolean v);
    void set(String k, Double v);
    void set(String k, Integer v);
    void set(String k, String v);
    void loadFromFile(String s);
    void saveToFile(String d);
}