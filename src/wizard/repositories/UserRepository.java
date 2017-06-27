package wizard.repositories;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2oException;
import wizard.utility.InvalidModelException;
import wizard.models.User;
import wizard.services.JSONConfigService;
import wizard.utility.QueryParam;

import java.util.ArrayList;
import java.util.List;


/**
 *  @author J. Sziegaud
 *  @author F. Engels
 *  TODO@all: description
 */
public class UserRepository extends SQLiteRepository implements Repository<User> {

    private static UserRepository instance;

    Integer lastInsertId = null;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    private UserRepository() {
        super(getDBUrl());
    }

    /**
     * Private helper that allows us to read the config before calling super()
     * @return
     */
    private static String getDBUrl() {
        JSONConfigService config = new JSONConfigService("config.json");
        return config.get("db.user.url");
    }

    /**
     * Adds a single user to the db
     * @param user A user that shall be added to the db
     */
    @Override
    public void add(User user) {
        try {

            user.validate(true);

            Connection c = getDBHC();

            this.lastInsertId = (Integer) c.createQuery(
            "insert into `Users` (name, username, password) " +
                     "values (:name, :username, :password)"
            )
            .addParameter("name", user.getName())
            .addParameter("username", user.getUsername())
            .addParameter("password", user.getPassword())
            .executeUpdate().getKey();

            c.getResult();

            user.setId(this.lastInsertId);

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidModelException e) {
            System.out.println("InvalidModelException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Adds multiple users to the db
     * @param users A <iterable> of users that shall be added to the db
     */
    @Override
    public void add(Iterable<User> users) {

        Connection c = getDBHC();

        Query q = c.createQuery(
                "insert into `Users` (name, username, password) " +
                        "values (:name, :username, :password)"
        );

        users.forEach(
                user ->  {

                    try {

                        user.validate(true);

                        q
                        .addParameter("name", user.getName())
                        .addParameter("username", user.getUsername())
                        .addParameter("password", user.getPassword())
                        .addToBatch();

                    } catch (Sql2oException e) {
                        System.out.println("Sql2oException: " + e.getMessage());
                        e.printStackTrace();
                    } catch (InvalidModelException e) {
                        System.out.println("InvalidModelException: " + e.getMessage());
                        e.printStackTrace();
                    }

                }
        );

        try {
            q.executeBatch();
            c.commit();
        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * Updates a specific record, specified by its model, from the db
     * @param user The model of the record to update
     */
    @Override
    public void update(User user) {
        try {

            user.validate();

            Connection c = getDBHC();

            c.createQuery(
                    "update `Users` " +
                             "set name=:name, username=:username, password=:password" +
                             "where id=:id"
            )
            .addParameter("name", user.getName())
            .addParameter("username", user.getUsername())
            .addParameter("password", user.getPassword())
            .addParameter("id", user.getId())
            .executeUpdate();

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidModelException e) {
            System.out.println("InvalidModelException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Removes a specific record, specified by its id, from the db
     * @param id The id of the record to remove
     */
    @Override
    public void remove(Integer id) {
        try {

            Connection c = getDBHC();

            c.createQuery(
                    "delete from `Users` " +
                            "where id=:id"
            )
            .addParameter("id", id)
            .executeUpdate();

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Removes a specific record, specified by its model, from the db
     * @param user The model of the record to remove
     */
    @Override
    public void remove(User user) {
        try {

            user.validate();

            Connection c = getDBHC();

            c.createQuery(
                    "delete * from `Users` " +
                            "where id=:id"
            )
            .addParameter("id", user.getId())
            .executeUpdate();

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidModelException e) {
            System.out.println("InvalidModelException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Clears the entire table
     */
    @Override
    public void flush() {
        try {

            Connection c = getDBHC();

            c.createQuery(
                    "delete * from `Users`"
            )
                    .executeUpdate();

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Fetches all records from the db
     * @return
     */
    @Override
    public List<User> get() {
        try {

            Connection c = getDBHC();

            return c.createQuery(
                    "select * from `Users`"
            ).executeAndFetch(User.class);

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }

    /**
     * Fetches a single record, specified by its id, from the db
     * @param id The id of the model to fetch
     * @return
     */
    @Override
    public User get(Integer id) {
        try {

            Connection c = getDBHC();

            List<User> r = c.createQuery(
                    "select * from `Users` " +
                            "where id=:id"
            )
            .addParameter("id", id)
            .executeAndFetch(User.class);

            if (!r.isEmpty())
                return r.get(0);

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        }

        return new User(null, null, null);
    }

    /**
     * *Should* work... - probably doesn't.
     * @param query The querystring. May include Sql2o style formatted params, such as :id
     * @param params A number of QueryParam objects that consist of a name:value pair
     * @return A list of user models, if applicable
     */
    @Override
    public List<User> query(String query, QueryParam... params) {
        try {

            Connection c = getDBHC();

            Query q = c.createQuery(
                query
            );

            for ( QueryParam param : params )
                q.addParameter(param.name, param.value);

            return q.executeAndFetch(User.class);

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }
}
