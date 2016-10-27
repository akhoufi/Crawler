package com.paris.sud.extraction;

import com.paris.sud.crawler.CrawlerUrl;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class WebPageTest {

    @Test
    public void testGetContent() throws Exception {
        String urlString="http://www.lemonde.fr/";
        WebPage page= new WebPage();
        CrawlerUrl url = new CrawlerUrl(urlString);
        String text = page.getContent(url);
        assertNotNull(text);
    }
}