package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Christoffer Buvik
 * Date: 11.03.14
 * Time: 10:38
 * Singleton for connection.
 */

public class DbConnection {
    private static Connection instance;

    static final String SQL_DRIVER = "com.mysql.jdbc.Driver";
    static final String SQL_URL = "jdbc:mysql://mysql.stud.ntnu.no/";
    static final String DB_NAME = "pedervl_fpdb";
    static final String USER_NAME = "pedervl_fp";
    static final String USER_PW = "fp14";

    /**
     * @return Connection
     */
    public static Connection getInstance() {
        if (instance == null) createInstance();
        return instance;
    }

    private static Connection createInstance() {
        if (instance == null) {
            try {
                Class.forName(SQL_DRIVER).newInstance();
                instance = DriverManager.getConnection(SQL_URL + DB_NAME, USER_NAME, USER_PW);
            } catch (InstantiationException ie) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ie);
            } catch (IllegalAccessException iae) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, iae);
            } catch (ClassNotFoundException nfe) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, nfe);
            } catch (SQLException e) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, e);
            }
        }
        return instance;
    }


}
