package com.example.smart_wallet.modules.user.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.smart_wallet.modules.user.domain.entity.User;
import com.example.smart_wallet.modules.wallet.domain.entity.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtTokenAdapterTest {

    private static final String SECRET = "unit-test-secret";

    private JwtTokenAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JwtTokenAdapter();
        ReflectionTestUtils.setField(adapter, "secret", SECRET);
    }

    private User userWithWallet(UUID walletId) {
        User user = new User();
        user.setEmail("pedro@example.com");
        Wallet wallet = new Wallet();
        wallet.setId(walletId);
        user.setWallet(wallet);
        return user;
    }

    @Test
    void generatesATokenContainingSubjectAndWalletClaim() {
        UUID walletId = UUID.randomUUID();
        User user = userWithWallet(walletId);
        user.setCpf("11144477735");
        user.setCellphone("11987654321");

        String token = adapter.generateToken(user);

        assertThat(token).isNotBlank();
        var decoded = JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer("smart-wallet-api")
                .build()
                .verify(token);
        assertThat(decoded.getSubject()).isEqualTo("pedro@example.com");
        assertThat(decoded.getClaim("walletId").asString()).isEqualTo(walletId.toString());
        assertThat(decoded.getClaim("profileComplete").asBoolean()).isTrue();
    }

    @Test
    void generatesATokenWithoutAWalletClaimAndProfileCompleteFalseWhenWalletAndDocumentsAreMissing() {
        User user = new User();
        user.setEmail("pedro@example.com");

        String token = adapter.generateToken(user);

        var decoded = JWT.require(Algorithm.HMAC256(SECRET))
                .withIssuer("smart-wallet-api")
                .build()
                .verify(token);
        assertThat(decoded.getClaim("walletId").isNull()).isTrue();
        assertThat(decoded.getClaim("profileComplete").asBoolean()).isFalse();
    }

    @Test
    void getEmailFromTokenStripsBearerPrefixAndReturnsTheSubject() {
        String token = adapter.generateToken(userWithWallet(UUID.randomUUID()));

        assertThat(adapter.getEmailFromToken("Bearer " + token)).isEqualTo("pedro@example.com");
        assertThat(adapter.getEmailFromToken(token)).isEqualTo("pedro@example.com");
    }

    @Test
    void getEmailFromTokenThrowsForAnInvalidToken() {
        assertThrows(RuntimeException.class, () -> adapter.getEmailFromToken("Bearer invalid"));
    }

    @Test
    void validateTokenReturnsTheSubject() {
        User user = userWithWallet(UUID.randomUUID());
        String token = adapter.generateToken(user);

        String subject = adapter.validateToken(token);

        assertThat(subject).isEqualTo("pedro@example.com");
    }

    @Test
    void validateTokenThrowsForATamperedToken() {
        assertThrows(RuntimeException.class, () -> adapter.validateToken("not-a-real-token"));
    }

    @Test
    void validateTokenThrowsForATokenSignedWithAnotherSecret() {
        String token = JWT.create()
                .withIssuer("smart-wallet-api")
                .withSubject("someone@example.com")
                .sign(Algorithm.HMAC256("another-secret"));

        assertThrows(RuntimeException.class, () -> adapter.validateToken(token));
    }

    @Test
    void getWalletIdFromTokenStripsBearerPrefix() {
        UUID walletId = UUID.randomUUID();
        String token = adapter.generateToken(userWithWallet(walletId));

        assertThat(adapter.getWalletIdFromToken("Bearer " + token)).isEqualTo(walletId.toString());
        assertThat(adapter.getWalletIdFromToken(token)).isEqualTo(walletId.toString());
    }

    @Test
    void getWalletIdFromTokenThrowsForAnInvalidToken() {
        assertThrows(RuntimeException.class, () -> adapter.getWalletIdFromToken("Bearer invalid"));
    }

    @Test
    void generateExpiresAtIsAboutTwoHoursFromNow() {
        Instant expected = Instant.now().plus(2, ChronoUnit.HOURS);

        Instant expiresAt = adapter.generateExpiresAt();

        assertThat(expiresAt).isCloseTo(expected, org.assertj.core.api.Assertions.within(5, ChronoUnit.SECONDS));
    }
}
