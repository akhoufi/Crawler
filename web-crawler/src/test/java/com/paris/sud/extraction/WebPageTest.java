package com.paris.sud.extraction;

import com.paris.sud.crawler.queuemanagement.QueueWithPriority;
import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import com.paris.sud.crawler.queuemanagement.model.UrlWithPriority;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

import static org.junit.Assert.*;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class WebPageTest {

    @Test
    public void testGetContent() throws Exception {
        CrawlerUrl url = new CrawlerUrl();
        PriorityBlockingQueue<UrlWithPriority> urlString= url.readInitialURLs();
        WebPage page= new WebPage();
        String text = page.getContent(urlString.poll().getUrl());
        assertNotNull(text);
    }
}