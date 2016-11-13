package com.paris.sud.transformation;

import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hadhami on 28/10/2016.
 */
public class TransformWebPage {
    private String urlString = null;
    private URL url = null;

    private String       htmlText;
    private Document htmlJsoupDoc;
    public  static String   niceText;
    private String       title;
    private List<String> linkList;

    public TransformWebPage(String urlString) {
        this.urlString = urlString;
    }

    public URL getURL() {
        return this.url;
    }

    public String getUrlString() {
        return this.urlString;
    }

    public String getNiceText() {
        return(niceText);
    }

    public String getTitle() {
        return(title);
    }

    public List<String> getLinks() {
        return(linkList);
    }

    public void setRawContent(String htmlText, String u) {
        CrawlerUrl url = new CrawlerUrl(u);
        String baseURL  = url.getURL() . toExternalForm();
        this . htmlText = htmlText;
        htmlJsoupDoc    = Jsoup. parse(htmlText,baseURL);
        title           = htmlJsoupDoc . title();
        niceText        = htmlJsoupDoc . body() . text();
        linkList        = new ArrayList<String>();
        Elements hrefJsoupLinks = htmlJsoupDoc . select("a[href]");
        for (Element link : hrefJsoupLinks) {
            String thisLink = link.attr("abs:href");
            if(thisLink . startsWith("http://")) {
                System.out.println("JSOUP Found: " + thisLink);
                linkList . add(thisLink);
            }
        }
    }

}
