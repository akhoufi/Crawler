package com.paris.sud.backup;

import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import com.paris.sud.crawler.queuemanagement.model.UrlWithPriority;

import java.io.*;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Hadhami on 13/11/2016.
 */
public class CollectionPersister {

    public final static String VISITED_PAGES_PATH = "/Users/Hadhami/aic/Recherche et extraction d'info/Projet/visited_pages.txt";
    public final static String QUEUE_PATH = "/Users/Hadhami/aic/Recherche et extraction d'info/Projet/queue.txt";
    public final static String NB_ITEMS_PATH = "/Users/Hadhami/aic/Recherche et extraction d'info/Projet/items.txt";


    public void persistVisitedPages(Set<String> visitedPages) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter((new File(VISITED_PAGES_PATH)), false));
        Iterator it = visitedPages.iterator();
        while (it.hasNext()) {
            writer.write((String) it.next());
            writer.newLine();
        }
        writer.close();
    }

    public void persistNbItems(int numberItemsSaved) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter((new File(NB_ITEMS_PATH)), false));
        writer.write(Integer.toString(numberItemsSaved));
        writer.newLine();
        writer.close();
    }

    public int loadNbItems() {
        int numberItemsSaved = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(NB_ITEMS_PATH)));
            numberItemsSaved = Integer.parseInt(reader.readLine());
            reader.close();
            return numberItemsSaved;
        } catch (Exception e) {
            return numberItemsSaved;
        }

    }

    public Set<String> loadVisitedPages() {
        HashSet<String> set = new HashSet<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(VISITED_PAGES_PATH)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!"".equals(line)) {
                    set.add(line);
                }
            }
            reader.close();
            return set;
        } catch (IOException e) {
            return set;
        }

    }

    public void persistQueue(PriorityBlockingQueue<UrlWithPriority> queue) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter((new File(QUEUE_PATH)), false));

        Iterator<UrlWithPriority> it = queue.iterator();
        while (it.hasNext()) {
            writer.write(it.next().toString());
            writer.newLine();
        }
        writer.close();
    }

    public PriorityBlockingQueue<UrlWithPriority> loadQueue() {
        PriorityBlockingQueue<UrlWithPriority> urlQueue = new PriorityBlockingQueue<UrlWithPriority>(99999999, new Comparator<UrlWithPriority>() {
            public int compare(UrlWithPriority x, UrlWithPriority y) {
                if (x.getPriority() < y.getPriority()) {
                    return -1;
                }
                if (x.getPriority() > y.getPriority()) {
                    return 1;
                }
                return 0;
            }
        });
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(QUEUE_PATH)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                try {
                    urlQueue.add(new UrlWithPriority(new CrawlerUrl(line.split(";")[0]), Integer.parseInt(line.split(";")[1])));
                } catch (Exception e) {

                }
            }
            reader.close();
            return urlQueue;
        } catch (IOException e) {
            return urlQueue;
        }

    }

}


