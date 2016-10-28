package com.paris.sud.transformation;

import com.paris.sud.crawler.Crawl;
import com.paris.sud.crawler.CrawlerUrl;
import com.paris.sud.extraction.WebPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Collection;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class PageWriter {

    public final String outputPath="/Users/Hadhami/aic/Recherche et extraction d'info/Projet/Files";
    private final String NL = System.getProperty("line.separator");

    public void saveContent(TransformWebPage transform)  throws Exception {

        WebPage webpage= new WebPage();
        CrawlerUrl url = new CrawlerUrl(transform.getUrlString());
        String content= webpage.getContent(url);
        transform.setRawContent(content,url.getUrlString());
        content = transform . getNiceText();
        String title   = transform . getTitle();
        Crawl crawl = new Crawl();
        String fileId=String.valueOf(crawl.getNumberItemsSaved());
        BufferedWriter contentOutput =
                new BufferedWriter(
                        new FileWriter(outputPath + "/download_"+ fileId +".txt"));
        contentOutput . write(content);
        contentOutput . flush();
        contentOutput . close();
        BufferedWriter titleOutput   =
                new BufferedWriter(
                        new FileWriter(outputPath + "/title_"+ fileId +".txt"));
        titleOutput   . write(title);
        titleOutput   . flush();
        titleOutput   . close();
        BufferedWriter urlOutput     =
                new BufferedWriter
                        (new FileWriter(outputPath + "/url_"+ fileId +".txt"));
        urlOutput     . write(url.getUrlString());
        urlOutput     . flush();
        urlOutput     . close();
    }

    public void saveLinks(TransformWebPage transform) throws Exception {
        Crawl crawl = new Crawl();
        CrawlerUrl url = new CrawlerUrl(transform.getUrlString());
        Collection<String> urlStrings = transform . getLinks();
        String fileId=String.valueOf(crawl.getNumberItemsSaved());
        BufferedWriter linksOutput =
                new BufferedWriter(
                        new FileWriter(outputPath + "/outlinkurls_"+ fileId +".txt"));
        for(String eachLink : urlStrings) {
            linksOutput . write(eachLink + NL);
        }
        linksOutput . flush();
        linksOutput . close();
    }
}
