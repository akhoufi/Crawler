package com.paris.sud.crawler;

import com.paris.sud.transformation.PageWriter;
import com.paris.sud.transformation.TransformWebPage;

import java.util.Queue;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class Crawl {

    public static int getNumberItemsSaved() {
        return numberItemsSaved;
    }

    public static int numberItemsSaved=0;

    public void crawl() throws Exception {

        CrawlerUrl crawler = new CrawlerUrl();
        PageWriter writer = new PageWriter();
        Queue<CrawlerUrl> queue =crawler.readURL();
        while (queue!=null) {
            CrawlerUrl url = queue.poll();
            if (url!=null){
                TransformWebPage transform = new TransformWebPage(url.getUrlString());
                writer.saveContent(transform);
                writer.saveLinks(transform);
                numberItemsSaved++;
            }else {
                break;
            }
        }

    }


}
