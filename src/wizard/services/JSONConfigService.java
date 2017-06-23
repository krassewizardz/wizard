package wizard.services;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;


/**
 *  @author F. Engels
 *  Provides developers with methods to query a JSON file, e.g the application's configuration.
 */
public class JSONConfigService implements ConfigServiceProvider {

    JSONObject config;

    /**
     *
     * @param key Name of the key to get
     * @return Either the value or null
     */
    @Override
    public String get(String key) {

        try {

            if ( key.contains(".") && !key.endsWith(".") ) {
                String[] parts = key.split("\\.");

                Integer pos = 0;
                JSONObject o = config;

                while (true) {
                    String k = parts[pos];

                    if (pos == parts.length - 1)
                        return o.get(k).toString();

                    o = o.getJSONObject(k);

                    pos++;
                }

            } else {
                return config.get(key).toString();
            }

        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Universal setter for various types
     * @param key
     * @param value
     * @param <pType>
     */
    private <pType> void _set(String key, pType value) {
        try {
            if ( key.contains(".") && !key.endsWith(".") ) {

                String[] parts = key.split("\\.");

                Integer pos = 0;
                JSONObject o = config;

                while (true) {
                    String k = parts[pos];

                    if (pos == parts.length - 1) {
                        o.put(k, value);
                        return;
                    }

                    o = o.getJSONObject(k);

                    pos++;
                }

            } else {
                config.put(key, value);
            }
        } catch (JSONException e) {
            return; // todo@all: (maybe) handle this
        }
    }

    /**
     *
     * @param key Name of the key
     * @param value Value to set
     */
    @Override
    public void set(String key, Boolean value) {
        _set(key, value);
    }

    /**
     *
     * @param key Name of the key
     * @param value Value to set
     */
    @Override
    public void set(String key, String value) {
        _set(key, value);
    }

    /**
     *
     * @param key Name of the key
     * @param value Value to set
     */
    @Override
    public void set(String key, Integer value) {
        _set(key, value);
    }

    /**
     *
     * @param key Name of the key
     * @param value Value to set
     */
    @Override
    public void set(String key, Double value) {
        _set(key, value);
    }

    /**
     *
     * @param source Path of the file to load the config from
     */
    @Override
    public void loadFromFile(String source) {

        byte[] rawContent;

        try {
            rawContent = Files.readAllBytes(Paths.get(source));
            String content = new String(rawContent);
            config = new JSONObject(content);
        } catch (IOException e) {
            System.out.println("IOException: " + e); // TODO@all: handle this
        } catch (JSONException e) {
            System.out.println("JSONException: " + e); // TODO@all: handle this
        }
    }

    /**
     *
     * @param destination Destination of the (new) File. If the file exists,
     *                    it's contents will be overwritten.
     */
    @Override
    public void saveToFile(String destination) {
        try {
            FileWriter fw = new FileWriter(destination);
            config.write(fw);
            fw.flush();
            fw.close();
        } catch (JSONException e) {
            System.out.println("JSONException: " + e); // TODO@all: handle this
        } catch (IOException e) {
            System.out.println("IOException: " + e); // TODO@all: handle this
        }
    }
}
