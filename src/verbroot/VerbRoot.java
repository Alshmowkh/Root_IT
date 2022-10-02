package verbroot;

import java.util.Scanner;

/**
 *
 * @author bakee
 */
public class VerbRoot {

    Dictionary dic;

    public VerbRoot() {
        dic=new Dictionary();
    }

    public VerbRoot(String pathDoc) {
        dic = new Dictionary();
        dic.path = pathDoc;
    }

    public void setDBpath(String pa) {
        dic = new Dictionary();
        dic.path = pa;
    }

    public String detect(String verb) {
        verb = verb.toLowerCase().trim();
        if (dic == null) {
            System.out.println("You must set database path first...");
//            return null;
            dic = new Dictionary();

        }
        String root = dic.getRoot(verb);
        return root;
    }
    public String detectByDB(String verb){
        verb = verb.toLowerCase().trim();
        if (dic == null) {
            System.out.println("You must set database path first...");
//            return null;
            dic = new Dictionary();

        }
        String root = dic.getRootDB(verb);
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

}
