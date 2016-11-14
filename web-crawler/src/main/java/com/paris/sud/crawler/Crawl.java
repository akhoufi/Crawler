package com.paris.sud.crawler;

import com.paris.sud.backup.CollectionPersister;
import com.paris.sud.crawler.queuemanagement.QueueWithPriority;
import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import com.paris.sud.crawler.queuemanagement.model.UrlWithPriority;
import com.paris.sud.indexation.Hash;
import com.paris.sud.indexation.IndexWriter;
import com.paris.sud.transformation.PageWriter;
import com.paris.sud.transformation.TransformWebPage;
import org.apache.http.client.HttpResponseException;
import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class Crawl {

    private PriorityBlockingQueue<UrlWithPriority> queue = null;
    private CollectionPersister persister = new CollectionPersister();
    private Set<String> visitedPages = persister.loadVisitedPages();

    public static int getNumberItemsSaved() {
        return numberItemsSaved;
    }

    private static int numberItemsSaved = 0;

    public void crawl() throws Exception {

        CrawlerUrl crawler = new CrawlerUrl();
        //CrawlingManager crawlingManager = new CrawlingManager();
        PageWriter writer = new PageWriter();
        Hash code = new Hash();
        numberItemsSaved = persister.loadNbItems();
        queue = persister.loadQueue();
        if (queue.size() == 0) {
            queue = crawler.readInitialURLs();
        }
        // for each host in the file
        // if you want to crawl the hole list, delete the condition for numberItemsSaved (numberItemsSaved < 11))
        while ((queue.size() != 0) ) {
            UrlWithPriority url = getNextUrl();
            if (url != null) {
                if (!"".equals(url.getUrl().getUrlString())) {
                    try {


                        TransformWebPage transform = new TransformWebPage(url.getUrl().getUrlString());
                        writer.saveContent(transform);
                        IndexWriter indexWriter = new IndexWriter();
                        indexWriter.write(url.getUrl().getUrlString(), code.hash(url.getUrl().getUrlString()));
                        addListToQueue(writer.saveLinks(transform), url.getPriority());


                        numberItemsSaved++;
                        visitedPages.add(url.getUrl().getUrlString());
                        //indexWriter.closeWriter();

                        // backup Collections
                        if ((numberItemsSaved % 1) == 0) {
                            persister.persistVisitedPages(visitedPages);
                            persister.persistQueue(queue);
                            persister.persistNbItems(numberItemsSaved);
                        }


                        Thread.sleep(100L);
                    } catch (ConnectTimeoutException e) {
                        System.out.println("can't crawl page for timeout reason");
                    } catch (Exception e) {
                        System.out.println("can't crawl page : " + e.getMessage());
                    }
                }

            } else {
                break;
            }
        }

    }


    public UrlWithPriority getNextUrl() {  // obtenir l'URL suivant a explorer
        UrlWithPriority nextUrl = null;
        while ((nextUrl == null) && (queue.size() != 0)) {
            UrlWithPriority crawlerUrl = this.queue.poll();
            boolean permission = true;
            try {
                CrawlingManager crawlingManager = new CrawlingManager();
                permission = crawlingManager.doWeHavePermissionToVisit(crawlerUrl.getUrl());
            } catch (IOException e) {
                permission = true;
            }
            boolean notVisited = !visitedPages.contains(crawlerUrl.getUrl().getUrlString());
            if ((permission) && (notVisited)) {
                //   && (crawlingManager.isUrlAlreadyVisited(crawlerUrl))) {
                nextUrl = crawlerUrl;
                System.out.println("Le prochain url a visiter est " + nextUrl.getUrl().getUrlString());
            }
        }
        return nextUrl;
    }

    public void addListToQueue(ArrayList<String> urlStrings, int priority) {
        for (int i = 0; i < urlStrings.size(); i++) {
            boolean permission = false;
            try {
                CrawlingManager crawlingManager = new CrawlingManager();
                permission = crawlingManager.doWeHavePermissionToVisit(new CrawlerUrl(urlStrings.get(i)));
            } catch (IOException e) {
                permission = false;
            }
            boolean notVisited = !visitedPages.contains(urlStrings.get(i));
            if ((permission) && (notVisited)) {
                queue.add(new UrlWithPriority(new CrawlerUrl(urlStrings.get(i)), priority + 1));
            }
        }
    }
}
