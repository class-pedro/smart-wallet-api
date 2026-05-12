package com.example.smart_wallet.controller;

import com.example.smart_wallet.dto.CreateCardDTO;
import com.example.smart_wallet.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.service.CardService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@AllArgsConstructor
public class CardController {
    private final CardService cardService;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CreateCardDTO cardDTO) {
        cardService.create(cardDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cards-to-input")
    public ResponseEntity<List<GetCardIdAndNameDTO>> getCardIdAndNameById(@RequestParam String walletId) {
        return ResponseEntity.ok(cardService.getCardIdAndNameById(walletId));
    }
}
