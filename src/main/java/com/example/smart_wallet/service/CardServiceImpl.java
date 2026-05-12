package com.example.smart_wallet.service;

import com.example.smart_wallet.domain.entity.Card;
import com.example.smart_wallet.domain.entity.CardType;
import com.example.smart_wallet.domain.entity.Wallet;
import com.example.smart_wallet.dto.CreateCardDTO;
import com.example.smart_wallet.dto.GetCardIdAndNameDTO;
import com.example.smart_wallet.infrastructure.repository.CardRepository.CardRepository;
import com.example.smart_wallet.infrastructure.repository.CardTypeRepository;
import com.example.smart_wallet.mapper.CreateCardMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;
    private CardTypeRepository cardTypeRepository;
    private WalletService walletService;

    @Override
    public Card getById(String cardId) {
        if (cardId == null) return null;
        UUID cardUUID = UUID.fromString(cardId);
        return cardRepository.findById(cardUUID).orElse(null);
    }

    @Override
    public List<GetCardIdAndNameDTO> getCardIdAndNameById(String cardId) {
        UUID cardUUID = UUID.fromString(cardId);
        return cardRepository.findIdsAndNameByWalletId(cardUUID);
    }

    @Override
    public void create(CreateCardDTO createCardDTO) {
        CardType cardType = cardTypeRepository
                .getCardTypeById(createCardDTO.getCardTypeId());
        validateCreateCardDTO(createCardDTO, cardType.getTitle());
        Wallet wallet = walletService.getById(String.valueOf(createCardDTO.getWalletId()));
        Card card = CreateCardMapper.toEntity(createCardDTO);
        card.setWallet(wallet);
        card.setCardType(cardType);

        cardRepository.save(card);
    }

    private void validateCreateCardDTO(CreateCardDTO createCardDTO, String cardTypeTitle) {
        Integer balance = createCardDTO.getBalance();
        Integer dueDateDay = createCardDTO.getDueDateDay();
        Integer closingDateDay = createCardDTO.getClosingDateDay();
        Integer creditLimit = createCardDTO.getCreditLimit();

        switch (cardTypeTitle) {
            case "credit":
                validateCreditCardDTO(balance, dueDateDay, closingDateDay, creditLimit);
                break;
            case "debit":
                validateDebitCardDTO(balance, dueDateDay, closingDateDay, creditLimit);
                break;
            case "multiple":
                validateMultipleCardDTO(balance, dueDateDay, closingDateDay, creditLimit);
                break;
            default:
                throw new RuntimeException("balance must be null to create a credit card");
        }
    }

    private void validateCreditCardDTO(Integer balance,
                                       Integer dueDateDay,
                                       Integer closingDateDay,
                                       Integer creditLimit) {
        if (balance != null) {
            throw new RuntimeException("balance must be null to create a credit card");
        }

        if (dueDateDay == null) {
            throw new RuntimeException("dueDateDay should not be null to create a credit card");
        }

        if (closingDateDay == null) {
            throw new RuntimeException("closingDateDay should not be null to create a credit card");
        }

        if (creditLimit == null) {
            throw new RuntimeException("creditLimit should not be null to create a credit card");
        }
    }

    private void validateDebitCardDTO(Integer balance,
                                      Integer dueDateDay,
                                      Integer closingDateDay,
                                      Integer creditLimit) {
        if (balance == null) {
            throw new RuntimeException("balance should not be null to create a debit card");
        }

        if (dueDateDay != null) {
            throw new RuntimeException("dueDateDay must be null to create a debit card");
        }

        if (closingDateDay != null) {
            throw new RuntimeException("closingDateDay must be null to create a debit card");
        }

        if (creditLimit != null) {
            throw new RuntimeException("creditLimit must be null to create a debit card");
        }
    }

    private void validateMultipleCardDTO(Integer balance,
                                         Integer dueDateDay,
                                         Integer closingDateDay,
                                         Integer creditLimit) {
        if (balance == null) {
            throw new RuntimeException("balance should not be null to create a debit card");
        }

        if (dueDateDay == null) {
            throw new RuntimeException("dueDateDay should not be null to create a credit card");
        }

        if (closingDateDay == null) {
            throw new RuntimeException("closingDateDay should not be null to create a credit card");
        }

        if (creditLimit == null) {
            throw new RuntimeException("creditLimit should not be null to create a credit card");
        }
    }
}
