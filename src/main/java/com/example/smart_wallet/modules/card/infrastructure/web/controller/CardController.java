package com.example.smart_wallet.modules.card.infrastructure.web.controller;

import com.example.smart_wallet.modules.card.application.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.modules.card.application.usecase.create.CreateCardUseCase;
import com.example.smart_wallet.modules.card.application.usecase.find.FindCardIdsAndNameByWalletUseCase;
import com.example.smart_wallet.modules.card.infrastructure.web.dto.CreateCardRequest;
import com.example.smart_wallet.modules.card.infrastructure.web.mapper.CreateCardWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@AllArgsConstructor
public class CardController {
    private final CreateCardUseCase createCardUseCase;
    private final FindCardIdsAndNameByWalletUseCase findCardIdsAndNameByWalletUseCase;

    @PostMapping
    public ResponseEntity<String> createCard(@RequestBody CreateCardRequest cardRequest) {
        createCardUseCase.execute(CreateCardWebMapper.toCommand(cardRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/cards-to-input")
    public ResponseEntity<List<GetCardIdAndNameDTO>> getCardIdAndNameById(@RequestParam String walletId) {
        return ResponseEntity.ok(findCardIdsAndNameByWalletUseCase.execute(walletId));
    }
}
