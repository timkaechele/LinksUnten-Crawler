package me.timkaechele.linksunten;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


public class Main {
    public static Crawler crawler;
    public static void main(String[] args) throws InterruptedException {

        URL firstURL = null;

        try {
            firstURL = new URL("https://linksunten.indymedia.org");
        } catch(MalformedURLException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }
        Queue<URL> queue = new ConcurrentLinkedQueue<URL>();
        Map<URL, Boolean> map = new ConcurrentHashMap<URL, Boolean>();

        queue.add(firstURL);
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for(int i = 0; i < 5; i++) {
            System.out.println("Starting Thread: " + i);

            Thread t = new Thread(new CrawlerThread(queue, map));
            threads.add(t);
            t.start();
            Thread.sleep(2000);
        }

    }


}
