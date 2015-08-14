package me.timkaechele.linksunten;

import java.net.URL;
import java.util.Map;
import java.util.Queue;

/**
 * Created by Tim on 13.08.15.
 */
public class CrawlerThread implements Runnable {

    private Queue<URL> queue;
    private Map<URL, Boolean> map;

    public CrawlerThread(Queue<URL> queue, Map<URL, Boolean> map) {
        this.queue = queue;
        this.map = map;
    }

    @Override
    public void run() {
        new Crawler(queue, map).start();
    }
}
