package com.example.smart_wallet.infrastructure.config;

import com.example.smart_wallet.domain.entity.CardType;
import com.example.smart_wallet.infrastructure.repository.CardTypeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CardTypeInitializer {

    @Bean
    CommandLineRunner initDatabase(CardTypeRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.saveAll(List.of(
                        new CardType(null, "credit"),
                        new CardType(null, "debit"),
                        new CardType(null, "multiple")
                ));
            }
        };
    }
}
