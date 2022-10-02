package verbroot;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static verbroot.Utile.closeConnection;
import static verbroot.Utile.conn;
import static verbroot.Utile.openConnection;
import static verbroot.Utile.pl;
import static verbroot.Utile.ps;
import static verbroot.Utile.rs;

public class Dictionary {

    String path;
    private List<String> Lines;
    private Stream<String> linesStrm;

    public Dictionary() {
        path = "./resources/Verbs02.database";
    }

    private void setDBpath(String pa) {
        path = pa;
    }

    public List<String> getAllLines() {

        try {
            List<String> lines = new ArrayList();

            File file = new File(path);
            FileReader fr = null;
            try {
                fr = new FileReader(file);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedReader bfr = new BufferedReader(fr);
            String line;
            int i = 0;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
//                Linesdediac.add(uts.deDiacritic(line));
            }
            Lines = lines;
            return lines;
        } catch (IOException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Stream<String> getAllLines2() {

        Stream<String> lines;
        File file = new File(path);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        BufferedReader bfr = new BufferedReader(fr);
        lines = bfr.lines();
//        Lines = lines.collect(Collectors.toList());
        return lines;
    }

    int maxOccuresLine(String verb) {
        if (Lines == null) {
            Lines = this.getAllLines();
//            System.out.println(LocalTime.now().getSecond());
        }
        verb = elaChecker(verb);
        int temp = 0, max = 1;
        int i = -1;
        int lineNo = i;
        String line;
        List<String> lineElements;
        Iterator<String> itr = Lines.iterator();
        System.out.println(LocalTime.now().getSecond());
        while (itr.hasNext()) {
            i++;
            line = itr.next();

            lineElements = this.getLineElements(line);
//        System.out.println(LocalTime.now().getSecond());

            temp = itemsCount(lineElements, verb);
            if (temp > max) {
                max = temp;
                lineNo = i;
            }
            if (lineNo >= 10) {
                break;
            }

        }
        System.out.println(LocalTime.now().getSecond());

        return lineNo;
    }

    int maxOccuresLine2(String verb) {
        if (linesStrm == null) {
            linesStrm = this.getAllLines2();
//            System.out.println(LocalTime.now().getSecond());
        }
        verb = elaChecker(verb);
        int temp = 0, max = 1;
        int i = -1;
        int lineNo = i;
        String line;
        List<String> lineElements;
        Iterator<String> itr = Lines.iterator();
        System.out.println(LocalTime.now().getSecond());
        while (itr.hasNext()) {
            i++;
            line = itr.next();

            lineElements = this.getLineElements(line);
//        System.out.println(LocalTime.now().getSecond());

            temp = itemsCount(lineElements, verb);
            if (temp > max) {
                max = temp;
                lineNo = i;
            }
            if (lineNo >= 10) {
                break;
            }

        }
        System.out.println(LocalTime.now().getSecond());

        return lineNo;
    }

    String getLine(int i) {
        if (Lines == null) {
            Lines = this.getAllLines();
        }
        return Lines.get(i);
    }

    String getRoot(String verb) {
        int lineroot = this.maxOccuresLine(verb);
        if (lineroot < 1) {
            return "فعل غير معروف.";
        }
        String line = this.Lines.get(lineroot);
        line = line.split("\\(")[0];
        return line;
    }

    String getRoot2(String verb) {
        Stream<String> lines;
        try {
            lines = Files.lines(Paths.get(path));
        } catch (IOException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
        }
        verb = this.elaChecker(verb);
//        line=lines.filter(null)
        int lineroot = this.maxOccuresLine2(verb);
        if (lineroot < 1) {
            return "فعل غير معروف.";
        }
        String line = this.Lines.get(lineroot);
        line = line.split("\\(")[0];
        return line;
    }

    private String elaChecker(String verb) {

        if (verb.length() == 3) {
            char middelElla = verb.charAt(1);
            if (middelElla == 'ا') {
                verb = verb.replace(middelElla, 'و');
            }

        } else if (verb.length() == 2) {
            verb = verb + verb.charAt(1);
        }
        return verb;
    }

    public List<String> getLineElements(String line) {
        List<String> lineElements = new ArrayList();
        String regex = "\\([^()]*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String[] elms;
        while (matcher.find()) {
            elms = matcher.group().replaceAll("\\(", "").replaceAll("\\)", "").split(",");
            for (String s : elms) {
                if (!s.isEmpty() && !s.equals("0")) {
                    lineElements.add(s);
                }
            }
        }
        return lineElements;
    }

    public int itemsCount(List<String> lineElements, String verb) {
        int counter = 0;
//        for (String s : lineElements) {
//            if (s.trim().matches(verb)) {
//                counter++;
//            }
//        }
//        while (lineElements.contains(verb)) {
//            counter++;
//            lineElements.remove(verb);
//        }

        for (Iterator<String> itr = lineElements.iterator(); itr.hasNext();) {
            if (itr.next().trim().equals(verb)) {
                counter++;
            }
        }
        return counter;
    }

    String getRootDB(String verb) {
        try {
            String root = "";

            openConnection();
            String stmt = "select root_id,verb_id,verb from verbs where VERB_NONDIAC='" + verb.trim() + "'";
            stmt = "select distinct verbs.root_id, root from verbs inner join roots on verbs.ROOT_ID=roots.ROOT_ID\n"
                    + " where verb_nondiac='" + verb.trim() + "'";

            ps = conn.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
//            root = (rs.getInt(1) + " : " + rs.getInt(2) + " : " + rs.getString(3));
                root += rs.getInt(1) + " : " + rs.getString(2);
//            pl(root);
            }
            closeConnection();

            if (root.isEmpty()) {
                return "فعل غير معروف.";
            } else {
                return root;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dictionary.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
