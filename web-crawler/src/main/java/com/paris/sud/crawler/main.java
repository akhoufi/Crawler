package com.paris.sud.crawler;

/**
 * Created by Hadhami on 14/11/2016.
 */
public class main {
    public static void main (String[] args){
        Crawl crawler= new Crawl();
        try {
            crawler.crawl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
