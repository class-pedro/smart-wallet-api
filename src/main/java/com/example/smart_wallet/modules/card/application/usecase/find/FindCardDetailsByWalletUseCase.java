package com.example.smart_wallet.modules.card.application.usecase.find;

import com.example.smart_wallet.modules.card.application.dto.GetCardDetailsDTO;

import java.util.List;

public interface FindCardDetailsByWalletUseCase {
    List<GetCardDetailsDTO> execute(String walletId);
}
