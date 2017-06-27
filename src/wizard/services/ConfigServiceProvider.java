package wizard.services;


public interface ConfigServiceProvider {
    String get(String k) throws Exception;
    void set(String k, Boolean v) throws Exception;
    void set(String k, Double v) throws Exception;
    void set(String k, Integer v) throws Exception;
    void set(String k, String v) throws Exception;
    void loadFromFile(String s);
    void saveToFile(String d);
}