package com.atharva.ragbot;

import com.atharva.ragbot.service.ChatService;
import com.atharva.ragbot.service.IngestionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication
public class RagBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(RagBotApplication.class, args);
    }

    @Bean
    CommandLineRunner runRagBot(IngestionService ingestionService, ChatService chatService) {
        return args -> {
            // 1. INGESTION (Learn)
            System.out.println("📚 Learning from the library...");
            ingestionService.ingestSite("http://books.toscrape.com/");

            // 2. INTERACTION (Chat)
            System.out.println("\n✅ I have read the library! Ask me anything about the books.");
            System.out.println("(Type 'exit' to quit)");

            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("\n👤 You: ");
                String question = scanner.nextLine();

                if (question.equalsIgnoreCase("exit")) break;

                String answer = chatService.askQuestion(question);
                System.out.println("🤖 Bot: " + answer);
            }
        };
    }
}