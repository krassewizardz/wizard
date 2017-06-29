package wizard.repositories;

import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2oException;
import wizard.models.Configuration;
import wizard.utility.InvalidModelException;
import wizard.models.User;
import wizard.services.JSONConfigService;
import wizard.utility.KeyNotFoundException;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *  @author J. Sziegaud
 *  @author F. Engels
 *  TODO@all: add documentation
 */
public class UserRepository extends SQLiteRepository implements Repository<User> {

    private static UserRepository instance;

    Integer lastInsertId = null;

    public static enum Column {
        ID,
        NAME,
        USERNAME,
        PASSWORD
    }

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
        try {
            JSONConfigService config = new JSONConfigService("config.json");
            return config.get("db.user.url");
        } catch (KeyNotFoundException e) {
            System.out.println("KeyNotFoundException: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a single user to the db
     * @param user A user that shall be added to the db
     */
    @Override
    public void add(User user) {
        try {

            JSONConfigService config = new JSONConfigService("config.json");
            String password = user.getPassword() + config.get("db.user.salt");

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes(Charset.forName("UTF-8")), 0, password.length());
            password = new BigInteger(1,md.digest()).toString(16);

            user.validate(true);
            Connection c = getDBHC();

            this.lastInsertId = (Integer) c.createQuery(
            "insert into `Users` (name, username, password) " +
                     "values (:name, :username, :password)"
            )
            .addParameter("name", user.getName())
            .addParameter("username", user.getUsername())
            .addParameter("password", password)
            .executeUpdate().getKey();

            c.getResult();

            user.setId(this.lastInsertId);

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidModelException e) {
            System.out.println("InvalidModelException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {

        }
    }

    public void add(Configuration config, User u) {
        try {
            Connection c = getDBHC();
            c.createQuery(
                    "insert into templates(" +
                            "user_id, name, scenario, outcome, competence, " +
                            "content, materials, comments, techniques, achievements)\n" +
                            "values (:user_id, :name, :scenario, :outcome, :competence, " +
                            ":content, :materials, :comments, :techniques, :achievements)")
                    .addParameter("user_id", u.getId())
                    .addParameter("name", config.getName())
                    .addParameter("scenario", boolToInt(config.isScenario()))
                    .addParameter("outcome", boolToInt(config.isOutcome()))
                    .addParameter("competence", boolToInt(config.isCompetence()))
                    .addParameter("content", boolToInt(config.isContent()))
                    .addParameter("materials", boolToInt(config.isMaterials()))
                    .addParameter("comments", boolToInt(config.isComments()))
                    .addParameter("techniques", boolToInt(config.isTechniques()))
                    .addParameter("achievements", boolToInt(config.isAchievements()))
                    .executeUpdate();
        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
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

        List<User> result = get(Column.ID, id);

        if (result.size() == 0)
            return result.get(0);

        return new User(null, null,null);
    }

    public List<Configuration> get(User u) {
        try {
            Connection c = getDBHC();
            List<Map<String, Object>> rawconfigurations = c.createQuery(
                    "SELECT id, name, scenario, outcome, " +
                    "competence, content, materials, comments, techniques, achievements\n" +
                    "FROM templates\n" +
                    "WHERE user_id = :user_id")
                    .addParameter("user_id", u.getId())
                    .executeAndFetchTable()
                    .asList();

            List<Configuration> configurations = new ArrayList<>();
            for (Map<String, Object> config : rawconfigurations) {
                int id = (int)config.get("id");
                String name = (String)config.get("name");
                int user_id = u.getId();
                boolean scenario = intToBool((int)config.get("scenario"));
                boolean outcome = intToBool((int)config.get("outcome"));
                boolean competence = intToBool((int)config.get("competence"));
                boolean content = intToBool((int)config.get("content"));
                boolean materials = intToBool((int)config.get("materials"));
                boolean comments = intToBool((int)config.get("comments"));
                boolean techniques = intToBool((int)config.get("techniques"));
                boolean achievements = intToBool((int)config.get("achievements"));
                configurations.add(new Configuration(
                        id,
                        name,
                        u.getId(),
                        scenario,
                        outcome,
                        competence,
                        content,
                        materials,
                        comments,
                        techniques,
                        achievements
                ));
            }
            return configurations;
        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    private boolean intToBool(int i) {
        return i != 0;
    }

    public List<User> get(Column column, Object value) {

        try {

            Connection c = getDBHC();

            List<User> result = c.createQuery(
                    "select * from `Users` " +
                            "where " + column.name().toLowerCase() +" =:value"
            )
            .addParameter("value", value)
            .executeAndFetch(User.class);

            return result;

        } catch (Sql2oException e) {
            System.out.println("Sql2oException: " + e.getMessage());
            e.printStackTrace();
        }

        return new ArrayList<User>();
    }
}
