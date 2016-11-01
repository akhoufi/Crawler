package com.paris.sud.crawler;

import com.paris.sud.indexation.Hash;
import com.paris.sud.indexation.IndexWriter;
import com.paris.sud.transformation.PageWriter;
import com.paris.sud.transformation.TransformWebPage;
import org.apache.http.conn.ConnectTimeoutException;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class Crawl {
    private static Queue<CrawlerUrl> queue = null;

    public static int getNumberItemsSaved() {
        return numberItemsSaved;
    }

    public static int numberItemsSaved = 0;

    public void crawl() throws Exception {

        CrawlerUrl crawler = new CrawlerUrl();
        CrawlingManager crawlingManager = new CrawlingManager();
        PageWriter writer = new PageWriter();
        Hash code = new Hash();
        queue = crawler.readURL();
        while (queue != null) {
            CrawlerUrl url = getNextUrl(crawlingManager);
            if (url != null) {
                if (!"".equals(url.getUrlString())) {
                    try {
                        TransformWebPage transform = new TransformWebPage(url.getUrlString());
                        writer.saveContent(transform);
                        IndexWriter indexWriter = new IndexWriter();
                        indexWriter.write(url.getUrlString(), code.hash(url.getUrlString()));
                        ArrayList<String> urlStrings = writer.saveLinks(transform);
                        if (urlStrings.size() > 10) {
                            for (int j = 0; j < 10; j++) {
                                try {
                                    String urlS = urlStrings.get(j);
                                    TransformWebPage transform1 = new TransformWebPage(urlS);
                                    indexWriter.write(urlS, code.hash(urlS));
                                    writer.saveContentLinks(transform1, j);
                                    urlStrings.addAll(writer.saveLinks(transform1));
                                    urlStrings.remove(j);
                                } catch (Exception e) {
                                    System.out.println("can't crawl second page ");
                                }

                            }
                        }
                        indexWriter.closeWriter();
                        numberItemsSaved++;
                        Thread.sleep(crawlingManager.getDelayBetweenUrls());
                    } catch (ConnectTimeoutException e) {
                        System.out.println("can't crawl primary page for timeout reason");
                    } catch (Exception e) {
                        System.out.println("can't crawl primary page ");
                    }


                }

            } else {
                break;
            }
        }

    }


    private CrawlerUrl getNextUrl(CrawlingManager crawlingManager) {  // obtenir l'URL suivant a explorer
        CrawlerUrl nextUrl = null;
        while ((nextUrl == null) && (!queue.isEmpty())) {
            CrawlerUrl crawlerUrl = this.queue.poll();
            if (crawlingManager.doWeHavePermissionToVisit(crawlerUrl)) {
                //   && (crawlingManager.isUrlAlreadyVisited(crawlerUrl))) {
                nextUrl = crawlerUrl;
                System.out.println("Le prochain url a visiter est " + nextUrl.getUrlString());
            }
        }
        return nextUrl;
    }


}
