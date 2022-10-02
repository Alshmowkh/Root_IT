package verbroot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utile {

    public static PreparedStatement ps;
    public static Connection conn;
    public static ResultSet rs;
    static String pathDB = "F:\\Master\\Thesis\\Implementations\\derbyDB\\sarfDB_10";

    public static boolean openConnection() {
        //sarfDB_10.rar
        String dbZip = "(F:\\Master\\Thesis\\Implementations\\derbyDB\\Alshmowkh\\sarfDB_10.zip) sarfDB_10";

        try {
            conn = DriverManager.getConnection("jdbc:derby:" + pathDB);
            System.setProperty("derby.storage.pageSize", "5000");
        } catch (SQLException ex) {
            pl("connection open error:\n" + ex.getMessage());
            return false;
        }
        return true;
    }

    public static boolean commit() {
        try {
            conn.commit();
        } catch (SQLException ex) {
            pl("commit error:\n" + ex.getMessage());
            return false;
        }
        return true;
    }

    public static boolean closeConnection() {
        try {
            conn.close();
        } catch (SQLException ex) {
            pl("connection close error:\n" + ex.getMessage());
            return false;
        }
        return true;
    }

    public static void pl(Object o) {
        System.out.println(o);
    }

    public List readFromFile() {
        List list = new ArrayList();

        return list;
    }

    public static double f_score(double p, double r) {

        return 2 * (p * r) / (p + r);
    }

}
