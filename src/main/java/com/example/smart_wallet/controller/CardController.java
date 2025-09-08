package com.example.smart_wallet.controller;

import com.example.smart_wallet.dto.CreateCardDTO;
import com.example.smart_wallet.service.card.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
@AllArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public void createCard(@RequestBody CreateCardDTO cardDTO) {
        cardService.create(cardDTO);
    }
}
