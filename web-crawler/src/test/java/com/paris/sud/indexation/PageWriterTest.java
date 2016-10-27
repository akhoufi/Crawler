package com.paris.sud.indexation;

import com.paris.sud.crawler.CrawlerUrl;
import com.paris.sud.extraction.WebPage;
import org.junit.Test;

import static org.junit.Assert.*;

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


    }
}