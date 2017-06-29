package wizard.services;

import wizard.models.User;
import wizard.repositories.UserRepository;
import wizard.utility.KeyNotFoundException;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;


/**
 *  @author F. Prinz
 *  @author F. Engels
 *  TODO@all: add documentation
 */
public class SQLiteAuthenthicationService implements AuthenticationServiceProvider {

    private static SQLiteAuthenthicationService instance = null;
    private User currentUser = null;

    private SQLiteAuthenthicationService() {}

    public static SQLiteAuthenthicationService getInstance() {
        if (instance == null)
            instance = new SQLiteAuthenthicationService();

        return instance;
    }

    @Override
    public boolean login(String username, String password) {

        try {
            JSONConfigService config = new JSONConfigService("config.json");
            password = password + config.get("db.user.salt");

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(Charset.forName("UTF-8")), 0, password.length());
            password = new BigInteger(1,md.digest()).toString(16);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException: " + e.getMessage());
            e.printStackTrace();
        } catch (KeyNotFoundException e) {
            System.out.println("KeyNotFoundException: " + e.getMessage());
            e.printStackTrace();
        }

        UserRepository ur = UserRepository.getInstance();

        try {
            List<User> result = ur.get(UserRepository.Column.PASSWORD, password);
            if (result.size() == 1) {
                currentUser = result.get(0);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void logout() {
        currentUser = null;
    }

    @Override
    public User getLoggedInUser() {
        return currentUser;
    }

    public boolean hasValidLogin() {
        return (currentUser != null);
    }

}