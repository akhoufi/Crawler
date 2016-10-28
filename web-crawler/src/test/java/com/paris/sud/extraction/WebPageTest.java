package com.paris.sud.extraction;

import com.paris.sud.crawler.CrawlerUrl;
import com.paris.sud.transformation.PageWriter;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Hadhami on 26/10/2016.
 */
public class WebPageTest {

    @Test
    public void testGetContent() throws Exception {
        CrawlerUrl url = new CrawlerUrl();
        Queue<CrawlerUrl> urlString=url.readURL();
        WebPage page= new WebPage();
        String text = page.getContent(urlString.element());
        assertNotNull(text);
    }
}