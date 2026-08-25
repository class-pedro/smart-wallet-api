package com.example.smart_wallet.modules.card.infrastructure.persistence;

import com.example.smart_wallet.modules.card.domain.entity.CardType;
import com.example.smart_wallet.modules.card.infrastructure.persistence.repository.CardTypeJpaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CardTypeInitializer {

    @Bean
    CommandLineRunner initDatabase(CardTypeJpaRepository repository) {
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
