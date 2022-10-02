/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corpus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static verbroot.Utile.pl;

/**
 *
 * @author bakee
 */
public class ReadFromFile {

//    String corpusPath;
//    public ReadFromFile(String path) {
//        corpusPath = path;
//    }
    public List sentences(String path) {
        File input = new File(path);
        List sentences = new ArrayList();
        if (path == null || path.isEmpty()) {
            pl("You must set file path befor...");
            return null;
        }
        if (input.isFile()) {
            sentences = this.getFileSentences(path);
        } else if (input.isDirectory()) {
            File[] files = input.listFiles();
            for (File file : files) {
                sentences.addAll(this.sentences(file.getPath()));
            }
        }
        return sentences;
    }

    public List getFileSentences(String path) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            String ls = System.getProperty("line.separator");

            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                }

            } finally {
                reader.close();
            }
        } catch (Exception e) {

        }
        List pureSent = new ArrayList<>();
        String[] sentences = stringBuilder.toString().split("\\.|:|\n|\\?");
        for (String sentence : sentences) {
            String sent = sentence.trim();
            if (!"".equals(sent)) {
                pureSent.add(sent + ".");
            }
        }
        return pureSent;
    }

    public List getFileLines(String path) {
        List lines = new ArrayList();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }

            } finally {
                reader.close();
            }
        } catch (Exception e) {

        }
        return lines;
    }

    public File saveInFile(List sentences, String path) {
        FileOutputStream fos = null;
        File output = null;

        try {
            output = new File(path);
            fos = new FileOutputStream(output);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            sentences.forEach(i -> {
                try {
                    bw.write(i.toString());
                    bw.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(ReadFromFile.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            );
        } catch (IOException ex) {

        }

        return output;
    }
}
