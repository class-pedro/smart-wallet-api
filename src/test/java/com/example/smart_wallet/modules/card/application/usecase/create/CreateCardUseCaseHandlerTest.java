package com.example.smart_wallet.modules.card.application.usecase.create;

import com.example.smart_wallet.modules.card.application.dto.CreateCardCommand;
import com.example.smart_wallet.modules.card.application.port.out.CardRepository;
import com.example.smart_wallet.modules.card.application.port.out.CardTypeRepository;
import com.example.smart_wallet.modules.card.domain.entity.Card;
import com.example.smart_wallet.modules.card.domain.entity.CardType;
import com.example.smart_wallet.modules.wallet.application.usecase.find.FindWalletUseCase;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCardUseCaseHandlerTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardTypeRepository cardTypeRepository;

    @Mock
    private FindWalletUseCase findWalletUseCase;

    @InjectMocks
    private CreateCardUseCaseHandler handler;

    private final UUID cardTypeId = UUID.randomUUID();
    private final UUID walletId = UUID.randomUUID();

    private CreateCardCommand command(Integer balance, Integer dueDateDay, Integer closingDateDay, Integer creditLimit) {
        return new CreateCardCommand("Card", balance, dueDateDay, closingDateDay, creditLimit, cardTypeId, walletId);
    }

    private CardType cardTypeWithTitle(String title) {
        CardType cardType = new CardType();
        cardType.setTitle(title);
        return cardType;
    }

    @Test
    void createsACreditCardWhenDataIsValid() {
        when(cardTypeRepository.getCardTypeById(cardTypeId)).thenReturn(cardTypeWithTitle("credit"));
        Wallet wallet = new Wallet();
        when(findWalletUseCase.execute(String.valueOf(walletId))).thenReturn(wallet);

        handler.execute(command(null, 10, 5, 1000));

        ArgumentCaptor<Card> captor = ArgumentCaptor.forClass(Card.class);
        verify(cardRepository).save(captor.capture());
        assertThat(captor.getValue().getWallet()).isEqualTo(wallet);
        assertThat(captor.getValue().getCardType().getTitle()).isEqualTo("credit");
    }

    @ParameterizedTest
    @MethodSource("invalidCreditCombinations")
    void rejectsInvalidCreditCardData(Integer balance, Integer dueDateDay, Integer closingDateDay, Integer creditLimit) {
        when(cardTypeRepository.getCardTypeById(cardTypeId)).thenReturn(cardTypeWithTitle("credit"));

        assertThrows(RuntimeException.class,
                () -> handler.execute(command(balance, dueDateDay, closingDateDay, creditLimit)));
        verifyNoInteractions(cardRepository);
    }

    static Stream<Arguments> invalidCreditCombinations() {
        return Stream.of(
                Arguments.of(500, 10, 5, 1000),
                Arguments.of(null, null, 5, 1000),
                Arguments.of(null, 10, null, 1000),
                Arguments.of(null, 10, 5, null)
        );
    }

    @Test
    void createsADebitCardWhenDataIsValid() {
        when(cardTypeRepository.getCardTypeById(cardTypeId)).thenReturn(cardTypeWithTitle("debit"));
        Wallet wallet = new Wallet();
        when(findWalletUseCase.execute(String.valueOf(walletId))).thenReturn(wallet);

        handler.execute(command(500, null, null, null));

        verify(cardRepository).save(org.mockito.ArgumentMatchers.any(Card.class));
    }

    @ParameterizedTest
    @MethodSource("invalidDebitCombinations")
    void rejectsInvalidDebitCardData(Integer balance, Integer dueDateDay, Integer closingDateDay, Integer creditLimit) {
        when(cardTypeRepository.getCardTypeById(cardTypeId)).thenReturn(cardTypeWithTitle("debit"));

        assertThrows(RuntimeException.class,
                () -> handler.execute(command(balance, dueDateDay, closingDateDay, creditLimit)));
        verifyNoInteractions(cardRepository);
    }

    static Stream<Arguments> invalidDebitCombinations() {
        return Stream.of(
                Arguments.of((Integer) null, null, null, null),
                Arguments.of(500, 10, null, null),
                Arguments.of(500, null, 5, null),
                Arguments.of(500, null, null, 1000)
        );
    }

    @Test
    void createsAMultipleCardWhenDataIsValid() {
        when(cardTypeRepository.getCardTypeById(cardTypeId)).thenReturn(cardTypeWithTitle("multiple"));
        lenient().when(findWalletUseCase.execute(String.valueOf(walletId))).thenReturn(new Wallet());

        handler.execute(command(500, 10, 5, 1000));

        verify(cardRepository).save(org.mockito.ArgumentMatchers.any(Card.class));
    }

    @ParameterizedTest
    @MethodSource("invalidMultipleCombinations")
    void rejectsInvalidMultipleCardData(Integer balance, Integer dueDateDay, Integer closingDateDay, Integer creditLimit) {
        when(cardTypeRepository.getCardTypeById(cardTypeId)).thenReturn(cardTypeWithTitle("multiple"));

        assertThrows(RuntimeException.class,
                () -> handler.execute(command(balance, dueDateDay, closingDateDay, creditLimit)));
        verifyNoInteractions(cardRepository);
    }

    static Stream<Arguments> invalidMultipleCombinations() {
        return Stream.of(
                Arguments.of((Integer) null, 10, 5, 1000),
                Arguments.of(500, null, 5, 1000),
                Arguments.of(500, 10, null, 1000),
                Arguments.of(500, 10, 5, null)
        );
    }

    @Test
    void rejectsUnknownCardTypeTitle() {
        when(cardTypeRepository.getCardTypeById(cardTypeId)).thenReturn(cardTypeWithTitle("unknown"));

        assertThrows(RuntimeException.class, () -> handler.execute(command(null, 10, 5, 1000)));
        verifyNoInteractions(cardRepository);
    }
}
