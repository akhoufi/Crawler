package com.paris.sud.crawler.queuemanagement;

import com.paris.sud.crawler.queuemanagement.model.CrawlerUrl;
import com.paris.sud.crawler.queuemanagement.model.UrlWithPriority;
import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by Hadhami on 13/11/2016.
 */
public class QueueWithPriorityTest {

    @Test
    public void testTest1() throws Exception {


        PriorityBlockingQueue<UrlWithPriority> queue = new PriorityBlockingQueue<UrlWithPriority>(100, new Comparator<UrlWithPriority>() {
            public int compare(UrlWithPriority x, UrlWithPriority y) {
                if (x.getPriority() < y.getPriority()) {
                    return -1;
                }
                if (x.getPriority() > y.getPriority()) {
                    return 1;
                }
                return 0;
            }
        });



        queue.add(new UrlWithPriority(new CrawlerUrl("priority 2"), 2));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 3"), 3));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 1"), 1));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 3 nb2"), 3));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 3 nb3"), 3));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 1 nb2"), 1));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 2 nb2"), 2));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 3 nb4"), 3));
        queue.add(new UrlWithPriority(new CrawlerUrl("priority 1 nb3"), 1));

        System.out.println("1st print");
        Iterator<UrlWithPriority> it = queue.iterator();
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }

        System.out.println("2nd print");
        while (queue.size() != 0) {
            System.out.println(queue.poll().getUrl().getUrlString());
        }

        System.out.println("3rd print");
        Iterator<UrlWithPriority> it2 = queue.iterator();
        while (it2.hasNext()) {
            System.out.println(it2.next().toString());
        }

        System.out.println("size : "  +queue.size());


    }
}