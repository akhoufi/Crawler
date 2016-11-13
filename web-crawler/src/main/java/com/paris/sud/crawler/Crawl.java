package com.paris.sud.crawler;

import com.paris.sud.backup.CollectionPersister;
import com.paris.sud.crawler.queuemanagement.QueueWithPriority;
import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import com.paris.sud.crawler.queuemanagement.model.UrlWithPriority;
import com.paris.sud.indexation.Hash;
import com.paris.sud.indexation.IndexWriter;
import com.paris.sud.transformation.PageWriter;
import com.paris.sud.transformation.TransformWebPage;
import org.apache.http.conn.ConnectTimeoutException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class Crawl {

    private QueueWithPriority<UrlWithPriority> queue = null;
    private CollectionPersister persister = new CollectionPersister();
    private Set<String> visitedPages = persister.loadVisitedPages();

    public static int getNumberItemsSaved() {
        return numberItemsSaved;
    }

    public static int numberItemsSaved = 0;

    public void crawl() throws Exception {

        CrawlerUrl crawler = new CrawlerUrl();
        CrawlingManager crawlingManager = new CrawlingManager();
        PageWriter writer = new PageWriter();
        Hash code = new Hash();
        queue = crawler.readInitialURLs();
        // for each host in the file
        while ((queue != null) && (numberItemsSaved < 10)) {
            UrlWithPriority url = getNextUrl(crawlingManager);
            if (url != null) {
                if (!"".equals(url.getUrl().getUrlString())) {
                    try {

                        visitedPages.add(url.getUrl().getUrlString());

                        TransformWebPage transform = new TransformWebPage(url.getUrl().getUrlString());
                        writer.saveContent(transform);
                        IndexWriter indexWriter = new IndexWriter();
                        indexWriter.write(url.getUrl().getUrlString(), code.hash(url.getUrl().getUrlString()));
                        addListToQueue(writer.saveLinks(transform), url.getPriority());


                        numberItemsSaved++;
                        //indexWriter.closeWriter();

                        // backup Collections
                        if ((numberItemsSaved % 5) == 0) {
                            persister.persistVisitedPages(visitedPages);
                        }


                        Thread.sleep(crawlingManager.getDelayBetweenUrls());
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


    public UrlWithPriority getNextUrl(CrawlingManager crawlingManager) {  // obtenir l'URL suivant a explorer
        UrlWithPriority nextUrl = null;
        while ((nextUrl == null) && (queue.size() != 0)) {
            UrlWithPriority crawlerUrl = this.queue.poll();
            if (crawlingManager.doWeHavePermissionToVisit(crawlerUrl.getUrl()) &&
                    (!visitedPages.contains(crawlerUrl.getUrl().getUrlString()))) {
                //   && (crawlingManager.isUrlAlreadyVisited(crawlerUrl))) {
                nextUrl = crawlerUrl;
                System.out.println("Le prochain url a visiter est " + nextUrl.getUrl().getUrlString());
            }
        }
        return nextUrl;
    }

    public void addListToQueue(ArrayList<String> urlStrings, int priority) {
        for (int i = 0; i < urlStrings.size(); i++) {
            queue.add(new UrlWithPriority(new CrawlerUrl(urlStrings.get(i)), priority + 1));
        }
    }
}
