package org.example.food.config;
import io.github.cdimascio.dotenv.Dotenv;

import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct; // ✅ Use jakarta instead of javax in Spring Boot 3+

@Configuration
public class EnvConfig {

    @PostConstruct
    public void init() {
        Dotenv dotenv = Dotenv.configure()
                .directory("./")   // look for .env in project root
                .ignoreIfMissing() // don’t fail if .env is not found
                .load();

        // Set each property
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });
    }
}
