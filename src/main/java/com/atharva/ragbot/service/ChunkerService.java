package com.atharva.ragbot.service;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChunkerService {

    // ✅ FIX: Use 'new' constructor
    private final DocumentSplitter splitter = DocumentSplitters.recursive(500, 50);

    public List<TextSegment> splitText(String rawText) {
        Document document = Document.from(rawText);
        List<TextSegment> segments = splitter.split(document);

        System.out.println("🔪 Chopped text into " + segments.size() + " segments.");
        if (!segments.isEmpty()) {
            String preview = segments.get(0).text();
            System.out.println("   🧩 First Chunk Preview: " + preview.substring(0, Math.min(50, preview.length())) + "...");
        }

        return segments;
    }
}