package wizard.models;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class User {

    private String name;
    private String username;
    private String password;

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

}
