package com.paris.sud.crawler.queuemanagement.model;

/**
 * Created by Hadhami on 13/11/2016.
 */
public class UrlWithPriority {
    CrawlerUrl url;
    int priority;

    public UrlWithPriority(CrawlerUrl url, int priority) {
        this.url = url;
        this.priority = priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setUrl(CrawlerUrl url) {
        this.url = url;
    }

    public CrawlerUrl getUrl() {
        return url;
    }

    public int getPriority() {
        return priority;
    }
}
