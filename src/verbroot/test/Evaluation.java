/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verbroot.test;

import assem_stemmer.SnowballStemmer;
import gpl.pierrick.brihaye.aramorph.AraMorph;
import gpl.pierrick.brihaye.aramorph.Solution;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import morphalizer.Morphalizer;
import morphalizer.Sentence;
import rootIT.VerbRoot;
import static verbroot.Utile.pl;

/**
 *
 * @author bakee
 */
public class Evaluation {

    String verbsFile;

    List getVerbs() {
        try {
            return Files.readAllLines(Paths.get(verbsFile));
        } catch (IOException ex) {
            Logger.getLogger(Evaluation.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    void ourSystem() {
        List<String> verbs = getVerbs();
        String verb, root;
//        String root=VerbRoot.detect("استطيع");
//        verbs.parallelStream().map(i-> VerbRoot.detect(i.toString()));
        VerbRoot rooter = new VerbRoot();
        rooter.loadVerbs();
        Iterator it = verbs.iterator();
        while (it.hasNext()) {
            verb = (String) it.next();
            root = rooter.detect2(verb);
            pl(root);
        }
//        List roots = verbs.parallelStream().map(i -> rooter(i)).collect(Collectors.toList());

//        pl(root);
//        pl(verbs.size());
    }

    void khoja() {
//        List<String> verbs = getVerbs();
//        String verb, root;
//        
//     ArabicStemmer arabicStemmer = new ArabicStemmer ( );
//        rooter.loadVerbs();
////        Iterator it = verbs.iterator();
////        while (it.hasNext()) {
////            verb = (String) it.next();
////            root = rooter.detect2(verb);
//            pl(root);
////        }
    }

    void madamira() {
        Sentence sent = null;
        List<String> verbs = getVerbs();
        String verb, root;
        Iterator it = verbs.iterator();
        while (it.hasNext()) {
            root = null;
            verb = (String) it.next();
            sent = Morphalizer.morphalize(verb);
            if (sent != null && !sent.isEmpty()) {
                if (sent.get(0) != null) {
                    root = sent.get(0).stem();
                }
            }
            pl(root);
        }
    }

    void buckwalter() {
        AraMorph aMorph = new AraMorph();
        ArrayList<String> stems = new ArrayList();

        List<String> verbs = getVerbs();
        String verb, root;
        HashSet hset;
        Iterator it = verbs.iterator();
        while (it.hasNext()) {
            stems = new ArrayList();
            verb = (String) it.next();
            if (aMorph.analyzeToken(verb)) {
                hset = aMorph.getWordSolutions(verb);
                Iterator itr = hset.iterator();
                while (itr.hasNext()) {
                    Solution sol = (Solution) itr.next();
                    String stem = sol.getStemArabicVocalization();
                    stems.add(stem);
                }
            }
            pl(stems);
        }
    }

    void arabicStemmer() {

    }

    void assemStemmer() {
        String stem = null;
        SnowballStemmer stemmer = null;
        try {
            Class cls = Class.forName("assem_stemmer.arabicStemmer");
            stemmer = (SnowballStemmer) cls.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
            Logger.getLogger(Evaluation.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (stemmer != null) {
            List<String> verbs = getVerbs();
            String verb, root;
            Iterator it = verbs.iterator();
            while (it.hasNext()) {
                verb = (String) it.next();
                stemmer.setCurrent(verb);
                stemmer.stem();
                stem = stemmer.getCurrent();
                pl(stem);
            }
        }
    }

    void ini() {
        verbsFile = "F:\\Master\\Thesis\\Prototype\\Papers\\Verb Root Identification for Arabic Text\\Dataset\\Arabic Treebank verbs pure.txt";

//        ourSystem();
//        khoja();
//        madamira();
//        buckwalter();
//        arabicStemmer();
//        this.assemStemmer();
    }

    public static void main(String[] args) {
        Evaluation cls = new Evaluation();
        cls.ourSystem("افادت");
//        cls.ini();
    }

    void ourSystem(String verb) {
//        List<String> verbs = getVerbs();
        String root;
//        String root=VerbRoot.detect("استطيع");
//        verbs.parallelStream().map(i-> VerbRoot.detect(i.toString()));
        VerbRoot rooter = new VerbRoot();
        rooter.loadVerbs();
//        Iterator it = verbs.iterator();
//        while (it.hasNext()) {
     
        root = rooter.detect2(verb);
        pl(root);
//        }
//        List roots = verbs.parallelStream().map(i -> rooter(i)).collect(Collectors.toList());

//        pl(root);
//        pl(verbs.size());
    }

}
