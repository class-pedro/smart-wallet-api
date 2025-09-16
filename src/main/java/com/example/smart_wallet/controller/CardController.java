package com.example.smart_wallet.controller;

import com.example.smart_wallet.dto.CreateCardDTO;
import com.example.smart_wallet.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.service.card.CardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cards")
@AllArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public void createCard(@RequestBody CreateCardDTO cardDTO) {
        cardService.create(cardDTO);
    }

    @GetMapping("/cards-to-input")
    public List<GetCardIdAndNameDTO> getCardIdAndNameById(@RequestParam String walletId) {
        return cardService.getCardIdAndNameById(walletId);
    }
}
