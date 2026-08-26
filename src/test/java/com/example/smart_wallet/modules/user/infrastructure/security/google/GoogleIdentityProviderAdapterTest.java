package com.example.smart_wallet.modules.user.infrastructure.security.google;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GoogleIdentityProviderAdapterTest {

    private final GoogleIdentityProviderAdapter adapter = new GoogleIdentityProviderAdapter("test-client-id");

    @Test
    void verifyThrowsForAMalformedIdToken() {
        assertThrows(IllegalArgumentException.class, () -> adapter.verify("not-a-real-jwt"));
    }

    @Test
    void verifyThrowsForABlankIdToken() {
        assertThrows(IllegalArgumentException.class, () -> adapter.verify(""));
    }
}
