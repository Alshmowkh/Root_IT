/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package verbroot.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Performance {

    String path = "F:\\Master\\Thesis\\Implementations\\CognateIdentifer\\resources\\Verbs02.database";
    static List<String> Lines;

    public List<String> getAllLines() {

        try {
            List<String> lines = new ArrayList();

            File file = new File(path);
            FileReader fr = null;
            try {
                fr = new FileReader(file);
            } catch (FileNotFoundException ex) {
            }
            BufferedReader bfr = new BufferedReader(fr);
            String line;
            int i = 0;
            while ((line = bfr.readLine()) != null) {
                lines.add(line);
            }
            Lines = lines;
            return lines;
        } catch (IOException ex) {
        }
        return null;
    }

    public String getLine(int linenum) {
        if (Lines != null) {
            return Lines.get(linenum);
        } else {
            return this.getAllLines().get(linenum);
        }
    }

    public Stream<String> getAllLines2() {

        Stream<String> lines;
        File file = new File(path);
        FileReader fr = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException ex) {
        }
        BufferedReader bfr = new BufferedReader(fr);
        lines = bfr.lines();
//        Lines = lines.collect(Collectors.toList());
        return lines;
    }

    public List<String> getElements(String line) {
        List<String> lineElements = new ArrayList();
        String regex = "\\([^()]*\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);
        String[] elms;
        String s;
        while (matcher.find()) {
            elms = matcher.group().replaceAll("\\(", "").replaceAll("\\)", "").split(",");

            for (Iterator<String> itr = Arrays.asList(elms).iterator(); itr.hasNext();) {
                s = itr.next();
                if (!s.isEmpty() && !s.equals("0")) {
                    lineElements.add(s);
                }
            }
        }
        return lineElements;
    }

    public int itemsCount(List<String> lineElements, String verb) {
        int counter = 0;
        for (Iterator<String> itr = lineElements.iterator(); itr.hasNext();) {
            if (itr.next().trim().equals(verb)) {
                counter++;
            }
        }
        return counter;
    }

    int maxOccuresLine2(String verb) {

        int temp = 0, max = 1;
        int i = -1;
        int lineNo = i;
        String line;
        List<String> lineElements, Lines = this.getAllLines();

        Iterator<String> itr = Lines.iterator();
        System.out.println(LocalTime.now().getSecond());
        while (itr.hasNext()) {
            i++;
            line = itr.next();

            lineElements = this.getElements(line);
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

    List<String> getElements(List<String> lines) {
        List<String> elements = new ArrayList();
        Iterator<String> itr = lines.iterator();
//        System.out.println(LocalTime.now().getSecond());
        while (itr.hasNext()) {
            elements.addAll(this.getElements(itr.next()));
        }
        return elements;
    }

    List<String> getElements(Stream<String> lines) {
        List<String> elements = new ArrayList();
        String regex;
//                regex= "\\([^()]*\\)";
        regex = "(.*?)[^(),a-zA-Z\\d*]*";
        Pattern pattern = Pattern.compile(regex);
//        Predicate<String> linePred = Pattern.compile(regex).asPredicate();
//        Stream<String> elms=lines.filter(linePred);
        Stream<String> elms;

//  Pattern.compile(regex).splitAsStream(lines.findFirst().get()).forEach(System.out::println);
//        Stream<String> elms = lines.parallel().flatMap(line -> {
//            return Pattern.compile(regex).splitAsStream(line);
//        });
//        elms = lines.flatMap(line -> MatcherStream.find(pattern, line));
//        Stream<MatchResult> sublines;
        lines.flatMap(line -> {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
//                elements.add(matcher.group());
                Stream.of(matcher.group());
            }
////            MatchResult mr = matcher.toMatchResult();
//            return Stream.of(matcher.toMatchResult()) ;
            return matcher.find() ? Stream.of(matcher.toMatchResult()) : null;
        })
                .flatMap(m -> Stream.of(m.group())) ////                .flatMap(mr -> {
                //                    mr.group()
                ////                );
                ////        }) // 
                //                .collect(Collectors.toList()) 
                .forEach(m -> System.out.println(m));
//            MatchResult mr = sublines.findFirst().get();

//        pl(elms.findFirst().get());
        return elements;
    }

    Stream<String> getElements2(Stream<String> lines) {
        List<String> elements;// = new ArrayList();
        String regex;
        regex = "(.*?)[^(),a-zA-Z\\d*]*";
        Pattern pattern = Pattern.compile(regex);
        Stream<String> lines2;
//        lines.map(line -> streaming(line)).forEach(l-> System.out.println(l.count()));
//        pl(lines.parallel().map(line -> streaming(line)).mapToInt(l -> CountItem(l, "استهدف")).max().getAsInt());

//        pl(lines.findFirst().get());
        return lines;
    }

    int getRootLineIndex(String verb) {
        Stream<String> lines = this.getAllLines2();
//        return lines.parallel().map(line -> streaming(line)).mapToLong(l -> CountItem(l, verb)).max().getAsLong();

        pl(lines.parallel().map(line -> streaming(line)).mapToInt(l -> countItem(l)).min().getAsInt());

        return 0;
    }

    void ini() {
        long start, end, sum;
        int rootlineIndex = 1545;
        String temp;
//        Stream<String> lines = getAllLines2();
        start = System.nanoTime();
//      Stream<String> lines = getAllLines2();
//        Stream<String> elements = this.getElements2(lines);
        rootlineIndex = this.getRootLineIndex("استهدف");
//        temp = getLine(rootlineIndex);
        temp = getLine(rootlineIndex);

        end = System.nanoTime();
//       
        pl((end - start) / 1000000 + "  millisecond");

        pl(temp);
        pl(rootlineIndex);
    }

   

    public static void main(String[] args) {
        Performance perf = new Performance();
        perf.ini();
   
    }

    long performance() {
//        return (LocalTime.now().getSecond() + LocalTime.now().getMinute() * 60);
        return System.nanoTime() / 10000000;
    }

    public static void pl(Object o) {
        System.out.println(o);
    }

    private Stream<String> streaming(String line) {
        return this.getElements(line).parallelStream();
    }

    private int countItem(Stream<String> l, String verb) {

        return (int) l.filter(v -> v.trim().equals(verb)).count();
//         pl( l.findFirst().get());
//        return 0;
    }

    private int countItem(Stream<String> l) {

        return (int) l.count();
//         pl( l.findFirst().get());
//        return 0;
    }

    private Stream<Object> lexicalized(Stream<String> v) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        pl(v.findFirst().get());
        return null;
    }
}
