package com.atharva.ragbot.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CrawlerService {

    private static final int MAX_DEPTH = 1;


    public List<String> crawlSite(String startUrl){
        System.out.println("🚀 Starting crawl for: " + startUrl);
        Set<String> visitedUrls = new HashSet<>();


        List<String> collectedText = new ArrayList<>();


        crawlPage(startUrl, 0, visitedUrls, collectedText);

        System.out.println("🏁 Done! Collected text from " + collectedText.size() + " pages.");
        return collectedText;
    }


    private void crawlPage(String url, int depth, Set<String> visitedUrls, List<String> collectedText){
        if(visitedUrls.contains(url) || depth > MAX_DEPTH) {
            return;
        }

        visitedUrls.add(url);
        System.out.println("🕷️ [" + depth + "] Visiting: " + url);

        try {
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(5000)
                    .get();

            String rawText = doc.body().text();
            String cleanText = cleanText(rawText);

            if (!cleanText.isEmpty()) {
                collectedText.add(cleanText);
            }

            Elements links = doc.select("a[href]");
            for(Element link : links){
                String nextUrl = link.attr("abs:href");
                if (!nextUrl.isEmpty() && nextUrl.startsWith("http")){
                    // Pass the SAME basket down
                    crawlPage(nextUrl, depth + 1, visitedUrls, collectedText);
                }
            }
        } catch(IOException e){
            System.err.println("❌ Error crawling " + url);
        }
    }

    private String cleanText(String text) {
        if (text == null) return "";
        return text.replaceAll("\\s+", " ").trim();
    }
}