package com.example.smart_wallet.service;

import com.example.smart_wallet.domain.entity.CardType;
import com.example.smart_wallet.infrastructure.repository.CardTypeRepository;
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
