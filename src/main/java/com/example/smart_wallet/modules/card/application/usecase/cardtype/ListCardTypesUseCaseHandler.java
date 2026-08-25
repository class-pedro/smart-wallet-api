package com.example.smart_wallet.modules.card.application.usecase.cardtype;

import com.example.smart_wallet.modules.card.application.port.out.CardTypeRepository;
import com.example.smart_wallet.modules.card.domain.entity.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListCardTypesUseCaseHandler implements ListCardTypesUseCase {
    private final CardTypeRepository cardTypeRepository;

    @Override
    public List<CardType> execute() {
        return cardTypeRepository.findAll();
    }
}
