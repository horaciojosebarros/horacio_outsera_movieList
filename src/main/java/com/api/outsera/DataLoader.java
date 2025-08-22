package com.api.outsera;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class DataLoader implements CommandLineRunner {

    private final FilmeRepository repository;

    public DataLoader(FilmeRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() > 0) return;

        ClassPathResource resource = new ClassPathResource("movielist.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String header = br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";", -1);
                if (parts.length < 5) continue;
                Integer year = parts[0].isEmpty() ? null : Integer.parseInt(parts[0].trim());
                String title = parts[1].trim();
                String studios = parts[2].trim();
                String produtores = parts[3].trim();
                String winnerStr = parts[4].trim();
                boolean winner = "yes".equalsIgnoreCase(winnerStr);
                repository.save(new Filmes(year, title, studios, produtores, winner));
            }
        }
    }
}
