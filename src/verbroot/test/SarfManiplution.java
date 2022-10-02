/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verbroot.test;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import verbroot.Dictionary;
import static verbroot.Utile.closeConnection;
import static verbroot.Utile.conn;
import static verbroot.Utile.openConnection;
import static verbroot.Utile.pl;
import static verbroot.Utile.ps;
import static verbroot.Utile.rs;
import verbroot.VerbRoot;

/**
 *
 * @author bakee
 */
public class SarfManiplution {

    Dictionary dic;

    public SarfManiplution() {
        dic = new Dictionary();
    }

    void imperative() {
        try {
            openConnection();

            String stmt = "select verb_nondiac from verbs where tense='I'";
            ps = conn.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                pl(rs.getString(1));
//                map.put(rs.getInt(1), rs.getInt(2));
            }

//        this.derbyZip();
            closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(SarfManiplution.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        SarfManiplution sarf = new SarfManiplution();
        sarf.imperative();

    }
}
