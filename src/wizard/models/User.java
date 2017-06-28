package wizard.models;


import wizard.utility.InvalidModelException;


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
    public void setPassword(String password) { this.password = password; }

    public User(String name, String username, String password  ) {
        this.name = name;
        this.username = username;
        setPassword(password);
    }

    public Boolean isValid() {
        return (id != null && name != null && username != null && password != null );
    }

    public Boolean isValid(boolean allowEmptyId) {
        return ((id != null || allowEmptyId) && name != null && username != null && password != null );
    }

    public void validate() throws InvalidModelException {
        if (!isValid()) throw new InvalidModelException("Model validation failed");
    }

    public void validate(boolean allowEmptyId) throws InvalidModelException {
        if (!isValid(allowEmptyId)) throw new InvalidModelException("Model validation failed");
    }

}
