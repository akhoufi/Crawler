package com.paris.sud.transformation;

import com.paris.sud.crawler.CrawlerUrl;
import org.junit.Test;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class PageWriterTest {

    @Test
    public void testSaveContent() throws Exception {
        String urlString="http://www.lemonde.fr/";
        PageWriter writer= new PageWriter();
        CrawlerUrl url = new CrawlerUrl(urlString);
        writer.saveContent(url);
        writer.saveLinks(url);
    }
}