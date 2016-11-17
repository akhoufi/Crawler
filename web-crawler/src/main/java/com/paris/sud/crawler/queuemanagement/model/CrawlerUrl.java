package com.paris.sud.crawler.queuemanagement.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

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

    public PriorityBlockingQueue<UrlWithPriority> readInitialURLs() {
        BufferedReader reader = null;
        // on se prend la liste des URLs a parcourir
        PriorityBlockingQueue<UrlWithPriority> urlQueue = new PriorityBlockingQueue<UrlWithPriority>(99999999, new Comparator<UrlWithPriority>() {
            public int compare(UrlWithPriority x, UrlWithPriority y) {
                if (x.getPriority() < y.getPriority()) {
                    return -1;
                }
                if (x.getPriority() > y.getPriority()) {
                    return 1;
                }
                return 0;
            }
        });
        try {
            File file = new File("/Users/Hadhami/aic/Recherche et extraction d'info/Projet/ULs");
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                urlQueue.add(new UrlWithPriority(new CrawlerUrl(line), 1));
            }
            reader.close();
            return urlQueue;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
