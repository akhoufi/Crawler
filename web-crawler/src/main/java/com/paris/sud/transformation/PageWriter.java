package com.paris.sud.transformation;

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

    public void saveContent(CrawlerUrl url)  throws Exception {
        WebPage webpage= new WebPage();
        String content= webpage.getContent(url);
        content = url . getNiceText();
        String title   = url . getTitle();
        String fileId="1";
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

    public void saveLinks(CrawlerUrl url) throws Exception {
        Collection<String> urlStrings = url . getLinks();
        String fileId = "1";
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
