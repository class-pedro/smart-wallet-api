package com.example.smart_wallet.modules.card.infrastructure.repository;

import com.example.smart_wallet.modules.card.domain.entity.Card;
import com.example.smart_wallet.modules.card.dto.GetCardIdAndNameDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import static com.example.smart_wallet.modules.card.infrastructure.repository.queries.CardRepositoryQueries.GET_CARD_IDS_AND_NAMES;
import static com.example.smart_wallet.modules.card.infrastructure.repository.queries.CardRepositoryQueries.GET_CARD_IDS_BY_WALLET_ID;

public interface CardRepository extends JpaRepository<Card, UUID> {
    @Query(value = GET_CARD_IDS_BY_WALLET_ID, nativeQuery = true)
    List<UUID> findIdsByWalletId(UUID walletId);

    @Query(value = GET_CARD_IDS_AND_NAMES, nativeQuery = true)
    List<GetCardIdAndNameDTO> findIdsAndNameByWalletId(UUID walletId);
}
