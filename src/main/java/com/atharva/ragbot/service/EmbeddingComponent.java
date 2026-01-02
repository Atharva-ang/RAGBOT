package com.atharva.ragbot.service;

// 👇 NOTICE THE NEW IMPORT PATH (includes "onnx")
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class EmbeddingComponent {

    @Bean
    public EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }
}