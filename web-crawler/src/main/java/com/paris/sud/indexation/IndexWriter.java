package com.paris.sud.indexation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Hadhami on 29/10/2016.
 */
public class IndexWriter {

    public final static String INDEX_PATH = "/Users/Hadhami/aic/Recherche et extraction d'info/Projet/Index.txt";
    private BufferedWriter writer = null;


    public void write(String url, int code) throws IOException {
        writer = new BufferedWriter(new FileWriter((new File(INDEX_PATH)), true));
        writer.write(code + "  \t  " + url);
        writer.newLine();
        writer.close();
    }


}