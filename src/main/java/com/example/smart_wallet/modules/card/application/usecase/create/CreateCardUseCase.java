package com.example.smart_wallet.modules.card.application.usecase.create;

import com.example.smart_wallet.modules.card.application.dto.CreateCardCommand;

public interface CreateCardUseCase {
    void execute(CreateCardCommand createCardCommand);
}
