package com.example.smart_wallet.modules.card.infrastructure.repository;

import com.example.smart_wallet.modules.card.domain.entity.CardType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CardTypeRepository extends JpaRepository<CardType, UUID> {
    CardType getCardTypeById(UUID id);
}
