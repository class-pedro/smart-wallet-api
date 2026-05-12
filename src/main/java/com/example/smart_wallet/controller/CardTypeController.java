package com.example.smart_wallet.controller;

import com.example.smart_wallet.domain.entity.CardType;
import com.example.smart_wallet.service.CardTypesService;
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
    private final CardTypesService cardTypesService;

    @GetMapping
    public ResponseEntity<List<CardType>> getCardIdAndNameById() {
        return ResponseEntity.ok(cardTypesService.listCardTypes());
    }
}
