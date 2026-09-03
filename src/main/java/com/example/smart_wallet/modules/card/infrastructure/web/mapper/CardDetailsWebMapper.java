package com.example.smart_wallet.modules.card.infrastructure.web.mapper;

import com.example.smart_wallet.modules.card.application.dto.GetCardDetailsDTO;
import com.example.smart_wallet.modules.card.infrastructure.web.dto.GetCardDetailsResponseDTO;
import com.example.smart_wallet.shared.MoneyNormalizer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class CardDetailsWebMapper {
    private static final DateTimeFormatter DUE_DATE_FORMATTER =
            DateTimeFormatter.ofPattern("d MMM", new Locale("pt", "BR"));

    private CardDetailsWebMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static List<GetCardDetailsResponseDTO> toResponse(List<GetCardDetailsDTO> cards) {
        return cards.stream().map(CardDetailsWebMapper::toResponse).toList();
    }

    private static GetCardDetailsResponseDTO toResponse(GetCardDetailsDTO card) {
        LocalDate currentClosingDate = card.getCurrentClosingDate().toLocalDate();
        LocalDate currentDueDate = card.getCurrentDueDate().toLocalDate();
        String status = LocalDate.now().isAfter(currentClosingDate) ? "fechada" : "aberta";

        return new GetCardDetailsResponseDTO(
                card.getId(),
                card.getName(),
                MoneyNormalizer.centsToReais(BigDecimal.valueOf(card.getCreditLimit())),
                MoneyNormalizer.centsToReais(card.getCurrentInvoice()),
                currentDueDate.format(DUE_DATE_FORMATTER),
                status
        );
    }
}
