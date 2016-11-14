package com.paris.sud.crawler;

import com.paris.sud.transformation.PageWriter;
import org.junit.Test;

import java.util.Queue;

import static org.junit.Assert.*;

/**
 * Created by Hadhami on 27/10/2016.
 */
public class CrawlTest {

    @Test
    public void testSaveContent() throws Exception {
        Crawl crawler= new Crawl();
        crawler.crawl();
    }
}