package com.example.smart_wallet.infrastructure.repository.CardRepository;

import com.example.smart_wallet.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import static com.example.smart_wallet.infrastructure.repository.CardRepository.queries.CardRepositoryQueries.GET_CARD_IDS_BY_WALLET_ID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    @Query(value = GET_CARD_IDS_BY_WALLET_ID, nativeQuery = true)
    List<UUID> findIdsByWalletId(UUID walletId);
}
