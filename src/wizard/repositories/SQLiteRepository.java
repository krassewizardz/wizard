package wizard.repositories;

import org.sql2o.Sql2o;
import org.sql2o.Connection;


public class SQLiteRepository {

    static Sql2o dbh = null;
    static Connection dbhc = null;

    protected SQLiteRepository(String url) {
        if (dbh == null)
            dbh = new Sql2o(url, null, null);

        if (dbhc == null)
            dbhc = dbh.open();
    }

    protected Sql2o getDBH() {
        return dbh;
    }

    protected Connection getDBHC() {
        return dbhc;
    }

}
