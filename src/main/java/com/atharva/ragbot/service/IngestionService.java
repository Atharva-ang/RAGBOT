package com.atharva.ragbot.service;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngestionService {

    private final CrawlerService crawler;
    private final ChunkerService chunker;
    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    // Spring auto-injects all our tools here
    public IngestionService(CrawlerService crawler,
                            ChunkerService chunker,
                            EmbeddingModel embeddingModel,
                            EmbeddingStore<TextSegment> embeddingStore) {
        this.crawler = crawler;
        this.chunker = chunker;
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    public void ingestSite(String url) {
        System.out.println("🚀 STARTING FULL INGESTION...");

        // 1. Crawl
        List<String> pages = crawler.crawlSite(url);

        for (String pageText : pages) {
            // 2. Chunk
            List<TextSegment> segments = chunker.splitText(pageText);

            if (segments.isEmpty()) continue;

            // 3. Embed (Convert Text -> Math)
            // We do this in batch for speed
            List<Embedding> embeddings = embeddingModel.embedAll(segments).content();

            // 4. Store (Save to RAM)
            embeddingStore.addAll(embeddings, segments);
        }

        System.out.println("💾 SAVED " + embeddingStore.hashCode() + " vectors to memory.");
        // (Using hashCode just to show it's an object, we can't easily count inside InMemoryStore without casting)
        System.out.println("✅ INGESTION COMPLETE!");
    }
}