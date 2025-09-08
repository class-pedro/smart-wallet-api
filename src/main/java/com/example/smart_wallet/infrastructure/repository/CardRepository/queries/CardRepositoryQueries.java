package com.example.smart_wallet.infrastructure.repository.CardRepository.queries;

public class CardRepositoryQueries {
    private CardRepositoryQueries() {
        throw new IllegalStateException("Utility class");
    }

    public static final String GET_CARD_IDS_BY_WALLET_ID = """
            SELECT id
            FROM cards
            WHERE wallet_id = :walletId
            """;
}
