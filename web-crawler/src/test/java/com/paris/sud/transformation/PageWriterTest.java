package com.paris.sud.transformation;

import com.paris.sud.crawler.CrawlerUrl;
import org.junit.Test;

import java.util.Queue;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class PageWriterTest {

    @Test
    public void testSaveContent() throws Exception {
        CrawlerUrl url = new CrawlerUrl();
        Queue<CrawlerUrl> urlString=url.readURL();
        PageWriter writer= new PageWriter();
        writer.saveContent(urlString.element());
        writer.saveLinks(urlString.element());
    }
}