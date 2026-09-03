package com.example.smart_wallet.modules.card.application.port.out;

import com.example.smart_wallet.modules.card.application.dto.GetCardDetailsDTO;
import com.example.smart_wallet.modules.card.application.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.modules.card.domain.entity.Card;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CardRepository {
    Optional<Card> findById(UUID id);

    Card save(Card card);

    List<UUID> findIdsByWalletId(UUID walletId);

    List<GetCardIdAndNameDTO> findIdsAndNameByWalletId(UUID walletId);

    List<GetCardDetailsDTO> findDetailsByWalletId(UUID walletId);
}
