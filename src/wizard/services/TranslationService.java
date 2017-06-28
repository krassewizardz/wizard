package wizard.services;

import wizard.utility.KeyNotFoundException;

/**
 *  @author F. Engels
 *  TODO@all: add documentation
 */
public class TranslationService {
    public static String translate(String key) {

        JSONConfigService config = new JSONConfigService("config.json");
        JSONConfigService translations = new JSONConfigService("translations.json");

        try {
            key = translations.get(config.get("language") + "." + key);
        } catch (KeyNotFoundException e) {
            System.out.println("KeyNotFoundException: " + e.getMessage());
            e.printStackTrace();
        }

        return key;
    }
}
