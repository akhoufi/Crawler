package com.paris.sud.transformation;

import com.paris.sud.crawler.Crawl;
import com.paris.sud.crawler.CrawlerUrl;
import com.paris.sud.extraction.WebPage;
import com.paris.sud.indexation.Hash;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class PageWriter {

    public final String outputPath="/Users/Hadhami/aic/Recherche et extraction d'info/Projet/";
    private final String NL = System.getProperty("line.separator");


    static Hash h = new Hash();



    public void saveContent(TransformWebPage transform)  throws Exception {
        WebPage webpage= new WebPage();
        CrawlerUrl url = new CrawlerUrl(transform.getUrlString());
        int code = h.hash(transform.getUrlString());
        String content= webpage.getContent(url);
        transform.setRawContent(content,url.getUrlString());
        content = transform . getNiceText();
        String title   = transform . getTitle();
        Crawl crawl = new Crawl();
        String fileId=String.valueOf(crawl.getNumberItemsSaved());
        for (int i=0;i<=crawl.getNumberItemsSaved();i++) {

            Path path = Paths.get(outputPath+code);
            Files.createDirectories(path);
            BufferedWriter contentOutput =
                    new BufferedWriter(
                            new FileWriter(path+"/"+code+"c.txt"));
            contentOutput.write(content);
            contentOutput.flush();
            contentOutput.close();
            BufferedWriter titleOutput =
                    new BufferedWriter(
                            new FileWriter(path+"/"+code+"t.txt"));
            titleOutput.write(title);
            titleOutput.flush();
            titleOutput.close();
            BufferedWriter urlOutput =
                    new BufferedWriter
                            (new FileWriter(path+"/"+code+"u.txt"));
            urlOutput.write(url.getUrlString());
            urlOutput.flush();
            urlOutput.close();
        }

    }


    public void saveContentLinks(TransformWebPage transform,int j)  throws Exception{
        Hash ha = new Hash();
        WebPage webpage= new WebPage();
        CrawlerUrl url = new CrawlerUrl(transform.getUrlString());

        int code = ha.hash(transform.getUrlString());
        String content= webpage.getContent(url);
        transform.setRawContent(content,url.getUrlString());
        content = transform . getNiceText();
        String title   = transform . getTitle();
        Crawl crawl = new Crawl();
        String fileId=String.valueOf(crawl.getNumberItemsSaved());

            BufferedWriter contentOutput =
                    new BufferedWriter(
                            new FileWriter(outputPath+h.getC()+"/"+ code +"c.txt"));
            contentOutput.write(content);
            contentOutput.flush();
            contentOutput.close();
            BufferedWriter titleOutput =
                    new BufferedWriter(
                            new FileWriter(outputPath+h.getC()+"/"+ code + "t.txt"));
            titleOutput.write(title);
            titleOutput.flush();
            titleOutput.close();
            BufferedWriter urlOutput =
                    new BufferedWriter
                            (new FileWriter(outputPath+h.getC()+"/"+ code +"u.txt"));
            urlOutput.write(url.getUrlString());
            urlOutput.flush();
            urlOutput.close();

    }


    public ArrayList<String> saveLinks(TransformWebPage transform) throws Exception {
        Crawl crawl = new Crawl();
        Hash ha = new Hash();
        CrawlerUrl url = new CrawlerUrl(transform.getUrlString());
        int code = ha.hash(transform.getUrlString());
        ArrayList<String> urlStrings = (ArrayList<String>) transform.getLinks();
        String fileId = String.valueOf(crawl.getNumberItemsSaved());
        for (int i = 0; i <= crawl.getNumberItemsSaved(); i++) {
            BufferedWriter linksOutput =
                    new BufferedWriter(
                            new FileWriter(outputPath+h.getC()+"/"+ code + "l.txt"));
            for (String eachLink : urlStrings) {
                linksOutput.write(eachLink + NL);
            }
            linksOutput.flush();
            linksOutput.close();
        }
        return urlStrings;
    }



}
