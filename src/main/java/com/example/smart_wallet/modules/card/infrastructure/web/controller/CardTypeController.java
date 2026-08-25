package com.example.smart_wallet.modules.card.infrastructure.web.controller;

import com.example.smart_wallet.modules.card.application.usecase.cardtype.ListCardTypesUseCase;
import com.example.smart_wallet.modules.card.domain.entity.CardType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/card-types")
@AllArgsConstructor
public class CardTypeController {
    private final ListCardTypesUseCase listCardTypesUseCase;

    @GetMapping
    public ResponseEntity<List<CardType>> getCardIdAndNameById() {
        return ResponseEntity.ok(listCardTypesUseCase.execute());
    }
}
