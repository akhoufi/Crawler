package com.paris.sud.crawler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class CrawlerUrl {


    private String urlString = null;
    private URL url = null;

    public CrawlerUrl() {
    }

    public CrawlerUrl(String urlString) {
        this.urlString = urlString;
        computeURL();
    }

    private void computeURL() {
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            // petit probleme
        }
    }

    public URL getURL() {
        return this.url;
    }

    public String getUrlString() {
        return this.urlString;
    }

    public Queue<CrawlerUrl> readURL(){
        BufferedReader reader = null;
        // on se prend la liste des URLs a parcourir
        Queue<CrawlerUrl> urlQueue = new LinkedList<CrawlerUrl>();
        try {
            File file = new File("/Users/Hadhami/aic/Recherche et extraction d'info/Projet/URLs");
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                urlQueue.add(new CrawlerUrl(line));
            }
            reader.close();
            return urlQueue;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public CrawlerUrl getNextUrl() {  // obtenir l'URL suivant a explorer
       CrawlerUrl url = new CrawlerUrl();
        CrawlerUrl nextUrl = null;
        while ((nextUrl == null) && (!url.readURL().isEmpty())) {
            CrawlerUrl crawlerUrl = url.readURL().element();
                nextUrl = crawlerUrl;
                System.out.println("Le prochain url a visiter est "+nextUrl);

        }
        return nextUrl;
    }

}
