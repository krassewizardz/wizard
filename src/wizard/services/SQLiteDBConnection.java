package wizard.services;

import org.sql2o.Sql2o;
import org.sql2o.Connection;
import wizard.utility.KeyNotFoundException;


public class SQLiteDBConnection {

    static private Sql2o dbh = null;
    static private Connection dbhc = null;
    static private SQLiteDBConnection instance = null;

    static public SQLiteDBConnection getInstance() {
        if (instance == null)
            instance = new SQLiteDBConnection(getDBUrl());

        return instance;
    }

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

    private SQLiteDBConnection(String url) {
        dbh = new Sql2o(url, null, null);
        dbhc = dbh.open();
    }

    public Sql2o getDBH() {
        return dbh;
    }

    public Connection getDBHC() {
        return dbhc;
    }

}
