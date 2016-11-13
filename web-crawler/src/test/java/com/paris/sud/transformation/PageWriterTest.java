package com.paris.sud.transformation;

import com.paris.sud.crawler.queuemanagement.QueueWithPriority;
import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import com.paris.sud.crawler.queuemanagement.model.UrlWithPriority;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Queue;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class PageWriterTest {

    @Test
    public void testSaveContent() throws Exception {
        CrawlerUrl url = new CrawlerUrl();
        QueueWithPriority<UrlWithPriority> urlString = url.readInitialURLs();
        PageWriter writer = new PageWriter();
        TransformWebPage transform = new TransformWebPage(urlString.poll().getUrl().getUrlString());
        writer.saveContent(transform);
        Collection<String> urlStrings = writer.saveLinks(transform);
    }

    @Test
    public void testGetHost() throws Exception {
        PageWriter writer = new PageWriter();
        String hostResult = writer.getHost("http://www.hollywoodreporter.com/topic/next-gen");
        String host = "http://www.hollywoodreporter.com/";
        assert (hostResult.equals(host));
    }
}


