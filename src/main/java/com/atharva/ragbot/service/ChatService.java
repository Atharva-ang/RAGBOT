package com.atharva.ragbot.service;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel; // 👈 New Import
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class ChatService {

    private final EmbeddingStore<TextSegment> embeddingStore;
    private final EmbeddingModel embeddingModel;
    private final ChatLanguageModel chatModel;

    public ChatService(EmbeddingStore<TextSegment> embeddingStore,
                       EmbeddingModel embeddingModel) {

        this.embeddingStore = embeddingStore;
        this.embeddingModel = embeddingModel;

        // 🦙 Build the Local Ollama Model
        this.chatModel = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434") // Points to your local Ollama app
                .modelName("llama3.2")             // The model you downloaded
                .timeout(Duration.ofSeconds(60))   // Give it time to think
                .temperature(0.7)
                .build();
    }

    public String askQuestion(String question) {
        System.out.println("🤔 User asked: " + question);

        // 1. Embed question
        Embedding questionEmbedding = embeddingModel.embed(question).content();

        // 2. Search relevant chunks
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .queryEmbedding(questionEmbedding)
                .maxResults(3)
                .build();

        EmbeddingSearchResult<TextSegment> result = embeddingStore.search(request);
        List<EmbeddingMatch<TextSegment>> relevant = result.matches();

        // 3. Create Prompt
        StringBuilder prompt = new StringBuilder();
        prompt.append("Answer the question based ONLY on the context below.\n\n");
        prompt.append("Context:\n");

        for (EmbeddingMatch<TextSegment> match : relevant) {
            prompt.append("- ").append(match.embedded().text()).append("\n");
        }

        prompt.append("\nQuestion: ").append(question);

        // 4. Generate Answer
        System.out.println("🦙 Sending prompt to Local Ollama...");
        String answer = chatModel.generate(prompt.toString());

        return answer;
    }
}