package com.paris.sud.backup;

import com.paris.sud.crawler.queuemanagement.QueueWithPriority;
import com.paris.sud.crawler.queuemanagement.model.UrlWithPriority;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Hadhami on 13/11/2016.
 */
public class CollectionPersister{

    public final static String VISITED_PAGES_PATH = "/Users/Hadhami/aic/Recherche et extraction d'info/Projet/visited_pages.txt";


    public void persistVisitedPages(Set<String> visitedPages) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter((new File(VISITED_PAGES_PATH)), false));
        Iterator it = visitedPages.iterator();
        while (it.hasNext()) {
            writer.write((String) it.next());
            writer.newLine();
        }
        writer.close();
    }


    public Set<String> loadVisitedPages() {
        HashSet<String> set = new HashSet<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(VISITED_PAGES_PATH)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                set.add(line);
            }
            return set;
        } catch (IOException e) {
            return set;
        }

    }

}


