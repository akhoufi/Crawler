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

    public static int getNumberItemsSaved() {
        return numberItemsSaved;
    }

    public static int numberItemsSaved = 0;

    public void crawl() throws Exception {

        CrawlerUrl crawler = new CrawlerUrl();
        PageWriter writer = new PageWriter();
        IndexWriter indexWriter = new IndexWriter();
        Hash code = new Hash();
        Queue<CrawlerUrl> queue = crawler.readURL();
        while (queue != null) {
            CrawlerUrl url = queue.poll();
            if (url != null) {
                TransformWebPage transform = new TransformWebPage(url.getUrlString());
                indexWriter.write(url.getUrlString(), code.hash(url.getUrlString()));
                writer.saveContent(transform);
                ArrayList<String> urlStrings = writer.saveLinks(transform);
                for (int j = 0; j < 10; j++) {
                    String urlS = urlStrings.get(j);
                    TransformWebPage transform1 = new TransformWebPage(urlS);
                    indexWriter.write(urlS, code.hash(urlS));
                    writer.saveContentLinks(transform1, j);
                    urlStrings.addAll(writer.saveLinks(transform1));
                    urlStrings.remove(j);
                }
                numberItemsSaved++;
            } else {
                break;
            }
        }
        indexWriter.closeWriter();
    }


}
