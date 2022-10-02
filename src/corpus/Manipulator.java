/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import morphalizer.Morphalizer;
import morphalizer.Sentence;
import morphalizer.Word;
import verbroot.Utile;
import static verbroot.Utile.pl;
import verbroot.VerbRoot;

/**
 *
 * @author bakee
 */
public class Manipulator {

    String corpusPath;

    void puringTreebankVerbs() {
        String pathfile = "F:\\Master\\Thesis\\Tools\\Parsers\\CoreNLP-master\\Alshmowkh\\Arabic Treebank tagged verbs pure.txt";
        List list = null;
        try {
//            list=Files.lines(Paths.get(pathfile)).filter(i-> i.split("\\s")[0].startsWith("ال")).collect(Collectors.toList());
            Files.lines(Paths.get(pathfile)).map(i -> i.split("\\t")[1]).forEach(System.out::println);

        } catch (IOException ex) {
            Logger.getLogger(Manipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
//        pl(list.size());
    }

    void calculateDistinctPATBroots() {
        Path path = Paths.get("F:\\Master\\Thesis\\Prototype\\Papers\\Verb Root Identification for Arabic Text\\Dataset\\ourSystem output v1.txt");
        List roots = null;
        try {
            roots = Files.readAllLines(path);

//            Files.lines(path).filter(i -> !i.trim().equals("null")).filter(i -> i.trim().length() == 3).forEach(System.out::println);
//            distinct=Files.lines(path).filter(i -> !distinct.contains(i));//.forEach(System.out::println);
        } catch (IOException ex) {
            Logger.getLogger(Manipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        List distinct = new ArrayList();
        Collections.sort(roots);
        Iterator it = roots.iterator();
        String temp;
        while (it.hasNext()) {
            temp = (String) it.next();
            if (!distinct.contains(temp)) {
                distinct.add(temp.trim());
                pl(temp);
            }
        }

//        pl(roots.size());
    }

    void readVerbsDB() {
        Path path=Paths.get("F:\\Master\\Thesis\\Implementations\\verbRoot\\resources\\Verbs02.database");
        List lines=null;
        try {
            lines=Files.readAllLines(path);
        } catch (IOException ex) {
            Logger.getLogger(Manipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        pl(lines.get(2000));
    }

    public static void main(String[] args) {
        Manipulator cls = new Manipulator();
//                pl(Utile.f_score(84.90, 85.88));

//        cls.calculateDistinctPATBroots();
//        cls.puringTreebankVerbs();
//        cls.extractVerbs().forEach(i-> pl(i));
//        cls.ini();
        cls.readVerbsDB();

    }

    void ini() {
        List verbs = getVerbs();
        VerbRoot rooting = new VerbRoot();
        verbs.forEach(i -> {
            String verb = i.toString();
            String root = rooting.detectByDB(verb);

            pl(verb + "\t" + root);
        });

    }

    List getVerbs() {
        return new ReadFromFile().getFileLines("./resources/verbs.txt");
    }

    List extractVerbs() {
        corpusPath = "./resources/Corpus/Dataset";
        List sentences = new ReadFromFile().sentences(corpusPath);
        File file = new ReadFromFile().saveInFile(sentences, "./resources/AllSenteces.txt");

//        sentences = Morphalizer.morphalize(file);
        Iterator it = sentences.iterator();
        sentences = new ArrayList();
        while (it.hasNext()) {
            Sentence s = Morphalizer.morphalize((String) it.next());
            for (Word w : s) {
                if (w.isVerb()) {
                    sentences.add(w.toString().replaceAll(" ", ""));
//                    pl(w);
                }
            }
        }
        file = new ReadFromFile().saveInFile(sentences, "./resources/verbs.txt");
        return sentences;
    }
}
