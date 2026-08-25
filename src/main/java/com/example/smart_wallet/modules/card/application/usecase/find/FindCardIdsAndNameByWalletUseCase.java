package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.dto.GetCardIdAndNameDTO;

import java.util.List;

public interface FindCardIdsAndNameByWalletUseCase {
    List<GetCardIdAndNameDTO> execute(String walletId);
}
