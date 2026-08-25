package com.example.smart_wallet.modules.card.application.usecase.create;

import com.example.smart_wallet.modules.card.application.dto.CreateCardCommand;
import com.example.smart_wallet.modules.card.application.mapper.CreateCardMapper;
import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import com.example.smart_wallet.modules.card.application.port.out.CardTypeRepository;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import com.example.smart_wallet.modules.card.domain.entity.CardType;
import com.example.smart_wallet.modules.wallet.application.usecase.find.FindWalletUseCase;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreateCardUseCaseHandler implements CreateCardUseCase {

    private CardRepository cardRepository;
    private CardTypeRepository cardTypeRepository;
    private FindWalletUseCase findWalletUseCase;

    @Override
    public void execute(CreateCardCommand createCardCommand) {
        CardType cardType = cardTypeRepository
                .getCardTypeById(createCardCommand.cardTypeId());
        validateCreateCardCommand(createCardCommand, cardType.getTitle());
        Wallet wallet = findWalletUseCase.execute(String.valueOf(createCardCommand.walletId()));
        Card card = CreateCardMapper.toEntity(createCardCommand);
        card.setWallet(wallet);
        card.setCardType(cardType);

        cardRepository.save(card);
    }

    private void validateCreateCardCommand(CreateCardCommand createCardCommand, String cardTypeTitle) {
        Integer balance = createCardCommand.balance();
        Integer dueDateDay = createCardCommand.dueDateDay();
        Integer closingDateDay = createCardCommand.closingDateDay();
        Integer creditLimit = createCardCommand.creditLimit();

        switch (cardTypeTitle) {
            case "credit":
                validateCreditCardCommand(balance, dueDateDay, closingDateDay, creditLimit);
                break;
            case "debit":
                validateDebitCardCommand(balance, dueDateDay, closingDateDay, creditLimit);
                break;
            case "multiple":
                validateMultipleCardCommand(balance, dueDateDay, closingDateDay, creditLimit);
                break;
            default:
                throw new RuntimeException("balance must be null to create a credit card");
        }
    }

    private void validateCreditCardCommand(Integer balance,
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

    private void validateDebitCardCommand(Integer balance,
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

    private void validateMultipleCardCommand(Integer balance,
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
