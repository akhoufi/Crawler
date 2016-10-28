package com.paris.sud.transformation;

import com.paris.sud.crawler.Crawl;
import com.paris.sud.crawler.CrawlerUrl;
import com.paris.sud.extraction.WebPage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class PageWriter {

    public final String outputPath="/Users/Hadhami/aic/Recherche et extraction d'info/Projet/Directory";
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
        for (int i=0;i<=crawl.getNumberItemsSaved();i++) {
            Path path = Paths.get(outputPath+i);
            Files.createDirectories(path);
            BufferedWriter contentOutput =
                    new BufferedWriter(
                            new FileWriter(outputPath +i+ "/download_" + i + ".txt"));
            contentOutput.write(content);
            contentOutput.flush();
            contentOutput.close();
            BufferedWriter titleOutput =
                    new BufferedWriter(
                            new FileWriter(outputPath +i+"/title_" + i + ".txt"));
            titleOutput.write(title);
            titleOutput.flush();
            titleOutput.close();
            BufferedWriter urlOutput =
                    new BufferedWriter
                            (new FileWriter(outputPath +i+ "/url_" + i + ".txt"));
            urlOutput.write(url.getUrlString());
            urlOutput.flush();
            urlOutput.close();
        }
    }

    public void saveContentLinks(TransformWebPage transform,int j)  throws Exception{
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
                            new FileWriter(outputPath +crawl.getNumberItemsSaved()+ "/download_" + crawl.getNumberItemsSaved() +j+".txt"));
            contentOutput.write(content);
            contentOutput.flush();
            contentOutput.close();
            BufferedWriter titleOutput =
                    new BufferedWriter(
                            new FileWriter(outputPath +crawl.getNumberItemsSaved()+"/title_" + crawl.getNumberItemsSaved() +j+ ".txt"));
            titleOutput.write(title);
            titleOutput.flush();
            titleOutput.close();
            BufferedWriter urlOutput =
                    new BufferedWriter
                            (new FileWriter(outputPath +crawl.getNumberItemsSaved()+ "/url_" + crawl.getNumberItemsSaved() +j+ ".txt"));
            urlOutput.write(url.getUrlString());
            urlOutput.flush();
            urlOutput.close();

    }


    public ArrayList<String> saveLinks(TransformWebPage transform) throws Exception {
        Crawl crawl = new Crawl();
        CrawlerUrl url = new CrawlerUrl(transform.getUrlString());
        ArrayList<String> urlStrings = (ArrayList<String>) transform.getLinks();
        String fileId = String.valueOf(crawl.getNumberItemsSaved());
        for (int i = 0; i <= crawl.getNumberItemsSaved(); i++) {
            BufferedWriter linksOutput =
                    new BufferedWriter(
                            new FileWriter(outputPath +i+ "/outlinkurls_" + i + ".txt"));
            for (String eachLink : urlStrings) {
                linksOutput.write(eachLink + NL);
            }
            linksOutput.flush();
            linksOutput.close();
        }
        return urlStrings;
    }
}
