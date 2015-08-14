package me.timkaechele.linksunten;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Tim on 13.08.15.
 */
public class Crawler {

    private Queue<URL> queue;
    private Map<URL, Boolean> fetched;
    private String prefix;

    public Crawler(Queue<URL> queue, Map<URL, Boolean> map) {
        this.prefix = "/Volumes/My Passport/LinksUnten/";
        this.queue = queue;
        this.fetched = map;
    }


    public void start() {
        while (queue.size() != 0) {
            URL url = queue.poll();
            Document doc = null;
            try {
                doc = Jsoup.connect(url.toString()).timeout(30000).get();
            } catch (IOException e) {
                System.out.println(e.getMessage());
                continue;
            }

            this.fetched.put(url, true);
            List<URL> links = this.getLinks(doc);

            links.removeAll(this.fetched.keySet());
            links.removeAll(this.queue);

            writePage(doc);

            this.queue.addAll(links);

        }
    }

    public Queue<URL> getQueue() {
        return this.queue;
    }

    public Map<URL, Boolean> getFetched() {
        return this.fetched;
    }

    public List<URL> getLinks(Document doc) {
        Elements links = doc.select("a");
        List<URL> output = new ArrayList<URL>();

        for (Element element : links) {
            URL url = null;

            try {
                URL baseURL = new URL("https://linksunten.indymedia.org");
                url = new URL(baseURL, element.attr("href"));
            } catch (MalformedURLException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (url.getHost().equals("linksunten.indymedia.org")) {
                output.add(url);
            }
        }
        return output;
    }

    public void writePage(Document doc) {
        UUID id = UUID.randomUUID();
        try {
            FileOutputStream fo = new FileOutputStream(this.prefix + id.toString() + ".html");
            OutputStreamWriter ow = new OutputStreamWriter(fo);
            Writer writer = new BufferedWriter(ow);
            writer.write(doc.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }


    }
}
