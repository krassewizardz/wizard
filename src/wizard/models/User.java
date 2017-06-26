package wizard.models;

import wizard.utility.InvalidModelException;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *  @author F. Engels
 *  TODO@all: description
 */
public class User {

    private Integer id = null;
    private String name = null;
    private String username = null;
    private String password = null;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) {

        try {
            byte[] bytes = password.getBytes(Charset.forName("UTF-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytes);
            this.password = thedigest.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public User(String name, String username, String password  ) {
        this.name = name;
        this.username = username;
        setPassword(password);
    }

    public Boolean isValid() {
        return (id != null && name != null && username != null && password != null );
    }

    public void validate() throws InvalidModelException {
        if (!isValid()) throw new InvalidModelException("Model validation failed");
    }

}
