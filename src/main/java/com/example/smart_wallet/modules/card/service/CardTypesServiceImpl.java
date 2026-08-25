package com.example.smart_wallet.modules.card.service;

import com.example.smart_wallet.modules.card.domain.entity.CardType;
import com.example.smart_wallet.modules.card.infrastructure.repository.CardTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CardTypesServiceImpl implements CardTypesService {
    private final CardTypeRepository cardTypeRepository;

    @Override
    public List<CardType> listCardTypes() {
        return cardTypeRepository.findAll();
    }
}
