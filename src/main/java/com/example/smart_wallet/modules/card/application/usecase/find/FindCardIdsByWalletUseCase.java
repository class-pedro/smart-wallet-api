package com.example.smart_wallet.modules.card.application.usecase.find;

import java.util.List;
import java.util.UUID;

public interface FindCardIdsByWalletUseCase {
    List<UUID> execute(UUID walletId);
}
