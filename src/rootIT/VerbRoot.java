package rootIT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import static morphalizer.Utile.deDiacritic;
import static verbroot.Utile.pl;

/**
 *
 * @author bakee
 */
public class VerbRoot {

    private static String db;
    private static List<String> verbs;

    public VerbRoot() {
        db = "F:\\Master\\Thesis\\Implementations\\verbRoot\\resources\\Verbs02.database";
    }

    public VerbRoot(String pathDoc) {
        db = pathDoc;
    }

    public void setDBpath(String pa) {
        db = pa;
    }

    private static String getRoot(String verb) {
        String root = null;
        Optional op = null;

        Path path = Paths.get("F:\\Master\\Thesis\\Implementations\\verbRoot\\resources\\Verbs02.database");
        try {
            op = Files.lines(path).parallel().filter(i -> i.contains(verb)).max((i, j) -> Integer.compare(i.split(verb).length, j.split(verb).length));//.forEach(i->pl(i));
        } catch (IOException ex) {
            pl("error in read verbs db...");
        }
        if (op != null && op.isPresent()) {
            root = (String) op.get();
            root = root.split("\\(")[0];
        }

        return root;
    }

    private String getRoot2(String verb) {
        String root = null;
        Optional op = null;
        if (verbs == null) {
            loadVerbs();
        }
        op = verbs.parallelStream().filter(i -> i.contains(verb)).max((i, j) -> Integer.compare(i.split(verb).length, j.split(verb).length));
        if (op != null && op.isPresent()) {
            root = (String) op.get();
            root = root.split("\\(")[0];
        }

        return root;
    }

    public static String detect(String verb) {
        verb = verb.toLowerCase();
        verb = deDiacritic(verb).trim();
        if (verb.length() == 3) {
            return verb;
        }
        if (verb.length() == 2) {
            return verb + verb.charAt(1);
        }

        String root = getRoot(verb);

        return root;
    }

    public String detect2(String verb) {
        verb = verb.toLowerCase();
        verb = deDiacritic(verb).trim();
        if (verb.length() == 3) {
            return verb;
        }
        if (verb.length() == 2) {
            return verb + verb.charAt(1);
        }
        String root = getRoot2(verb);

        return root;
    }

    void processing() {
        String verb = "فاز";
        while (true) {
            System.out.print("Enter a verb: ");
            verb = new Scanner(System.in, "windows-1256").nextLine();
            if (verb.equals("")) {
                break;
            }
            System.out.println(detect(verb));
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VerbRoot d = new VerbRoot();
        if (args.length > 0) {
            System.out.println(d.detect(args[0].trim()));
        } else {
            d.processing();
        }
    }

    public void loadVerbs() {
        Path path = Paths.get("F:\\Master\\Thesis\\Implementations\\verbRoot\\resources\\Verbs02.database");
        try {
            verbs = Files.readAllLines(path);
        } catch (IOException ex) {
            Logger.getLogger(VerbRoot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
/*
 مشكلات نظام اكتشاف الجذر
 1- عدم القدرة على اكتشاف جذر افعال الجمل التالية  
 1- تمر في المثال تمر الايام مرور السحب 
 2- الفعل يكتب في  المثال يكتب محمد كتابة الطابعة
 3- الفعل سير في سرت سير السلحفاة
 4- الفعل يتلون
 5- الفعل المعتل صاح
 6- الفعل يعذب في الجمله يعذبه الله العذاب الاكبر
 7- الفعل سعى في الجملة وسعى لها سعيها
    
 2- 
 */
