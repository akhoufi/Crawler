package com.paris.sud.crawler;

import com.paris.sud.indexation.Hash;
import com.paris.sud.indexation.IndexWriter;
import com.paris.sud.transformation.PageWriter;
import com.paris.sud.transformation.TransformWebPage;

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
        IndexWriter indexWriter = new IndexWriter();
        Hash code = new Hash();
        queue = crawler.readURL();
        while (queue != null) {
            CrawlerUrl url = getNextUrl(crawlingManager);
            if (url != null) {
                if (!"".equals(url.getUrlString())) {
                    TransformWebPage transform = new TransformWebPage(url.getUrlString());
                    indexWriter.write(url.getUrlString(), code.hash(url.getUrlString()));
                    writer.saveContent(transform);
                    ArrayList<String> urlStrings = writer.saveLinks(transform);
                    if (urlStrings.size() > 10) {
                        for (int j = 0; j < 10; j++) {
                            String urlS = urlStrings.get(j);
                            TransformWebPage transform1 = new TransformWebPage(urlS);
                            indexWriter.write(urlS, code.hash(urlS));
                            writer.saveContentLinks(transform1, j);
                            urlStrings.addAll(writer.saveLinks(transform1));
                            urlStrings.remove(j);
                        }
                    }
                    numberItemsSaved++;
                }

            } else {
                break;
            }
        }
        indexWriter.closeWriter();
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
