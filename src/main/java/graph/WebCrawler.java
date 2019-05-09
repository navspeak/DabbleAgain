package graph;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    Queue<String> q;
    Set<String> visitedSites;
    AtomicInteger i = new AtomicInteger(0);

    public WebCrawler() {
        this.q = new LinkedList();
        this.visitedSites = new HashSet<>();
    }
    
    public void discoverWeb(String root){
        
        q.add(root);
        visitedSites.add(root);
        while(!q.isEmpty()){
            String curr = q.remove();
            String rawHtml = readURL(curr);

            Pattern pattern = Pattern.compile("http(s)?://(\\w+\\.)*(\\w+)/?");
            Matcher matcher = pattern.matcher(rawHtml);
            while(matcher.find()){
                String url = matcher.group();
                if (!visitedSites.contains(url)){
                    visitedSites.add(url);
                    System.out.println(i.incrementAndGet() + ". Found " + url);
                    q.add(url);
                }
            }
        }
    }

    private String readURL(String url) {
        final StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        (new URL(url)).openStream()))) {
            br.lines().forEach(sb::append);

        } catch (IOException e){
            System.out.println("ERROR " + e.getMessage());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        WebCrawler wc = new WebCrawler();
        wc.discoverWeb("https://www.hotstar.com/");
    }

}
