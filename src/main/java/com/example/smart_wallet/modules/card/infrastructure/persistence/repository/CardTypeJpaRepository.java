package com.example.smart_wallet.modules.card.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.card.application.port.out.CardTypeRepository;
import com.example.smart_wallet.modules.card.domain.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardTypeJpaRepository extends JpaRepository<CardType, UUID>, CardTypeRepository {
    @Override
    CardType getCardTypeById(UUID id);
}
