package wizard.services;

import org.sql2o.Connection;
import org.sql2o.Sql2o;

/**
 * Created by jansziegaud on 23.06.17.
 */
public class SQL2ODBServiceProvider implements DBServiceProvider<Connection> {

    private static Sql2o sql2o;

    static {
        sql2o = new Sql2o("");
    }

    @Override
    public Connection open() {
        try {
            return sql2o.open();
        } catch (Exception e) {
            throw e;
        }
    }
}
