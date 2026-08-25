package com.example.smart_wallet.modules.card.infrastructure.persistence.repository;

import com.example.smart_wallet.modules.card.application.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

import static com.example.smart_wallet.modules.card.infrastructure.persistence.repository.queries.CardRepositoryQueries.GET_CARD_IDS_AND_NAMES;
import static com.example.smart_wallet.modules.card.infrastructure.persistence.repository.queries.CardRepositoryQueries.GET_CARD_IDS_BY_WALLET_ID;

public interface CardJpaRepository extends JpaRepository<Card, UUID>, CardRepository {
    @Override
    @Query(value = GET_CARD_IDS_BY_WALLET_ID, nativeQuery = true)
    List<UUID> findIdsByWalletId(UUID walletId);

    @Override
    @Query(value = GET_CARD_IDS_AND_NAMES, nativeQuery = true)
    List<GetCardIdAndNameDTO> findIdsAndNameByWalletId(UUID walletId);
}
