package verbroot.test;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import static verbroot.Utile.*;

public class PerformanceDerby {

    void ini() throws SQLException {
        long start, end;
        String rootText;
        start = System.nanoTime();

        rootText = this.getRoot("استخدم");

        end = System.nanoTime();
//       
        pl((end - start) / 1000000 + "  millisecond");

        pl(rootText);
    }

    public static void main(String[] args) throws SQLException {
        PerformanceDerby perf = new PerformanceDerby();
        perf.getAllVerbPatterns();
//        perf.ini();
//        perf.derbyZip();

    }

    void getAllVerbPatterns() throws SQLException {
       String word="يستخدم";
        String root = null;
        Map map = new HashMap();
        openConnection();
        String stmt = "select root_id,verb_id from verbs where verb_nondiac='" + word.trim() + "'";
//                 stmt = "select max(verb_id) as max from verbs";

        ps = conn.prepareStatement(stmt);
        rs = ps.executeQuery();
        while (rs.next()) {
            pl(rs.getInt(1) + " : " + rs.getInt(2));
//                map.put(rs.getInt(1), rs.getInt(2));
        }

//        this.derbyZip();
        closeConnection();
//        root = rootsMap(map);
    }

    private String getRoot(String word) throws SQLException {
        String root = null;
        Map map = new HashMap();
        openConnection();
        String stmt = "select root_id,verb_id from verbs where verb_nondiac='" + word.trim() + "'";
//                 stmt = "select max(verb_id) as max from verbs";

        ps = conn.prepareStatement(stmt);
        rs = ps.executeQuery();
        while (rs.next()) {
            pl(rs.getInt(1) + " : " + rs.getInt(2));
//                map.put(rs.getInt(1), rs.getInt(2));
        }

//        this.derbyZip();
        closeConnection();
//        root = rootsMap(map);

        return root;
    }

    private String rootsMap(Map map) {

        Map countMap = new HashMap();
        Iterator itr = map.values().parallelStream().distinct().iterator();
        Object root;
        int counter = 0;
        while (itr.hasNext()) {
            root = itr.next();
            while (map.containsValue(root)) {
                counter++;
                map.remove(root, root);
            }
            countMap.put(itr.next(), map);
        }

        return null;
    }

    private void derbyZip() {
        Properties p = System.getProperties();
        pl(p.getProperty("derby.storage.pageSize"));
    }
}
