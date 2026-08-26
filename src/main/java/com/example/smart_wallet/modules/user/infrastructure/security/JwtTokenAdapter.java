package com.example.smart_wallet.modules.user.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.smart_wallet.modules.user.application.port.out.TokenGenerator;
import com.example.smart_wallet.modules.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class JwtTokenAdapter implements TokenGenerator, com.example.smart_wallet.infrastructure.security.TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Override
    public String generateToken(User user) {
        try {
            Instant expiresAt = generateExpiresAt();
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String walletId = user.getWallet() != null ? user.getWallet().getId().toString() : null;
            String token = JWT.create()
                    .withIssuer("smart-wallet-api")
                    .withSubject(user.getEmail())
                    .withClaim("walletId", walletId)
                    .withClaim("profileComplete", user.isProfileComplete())
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error generating token", exception);
        }
    }

    @Override
    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("smart-wallet-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Error validating token", exception);
        }
    }

    @Override
    public String getWalletIdFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            validateToken(token);
            DecodedJWT decoded = JWT.decode(token);
            return decoded.getClaim("walletId").asString();
        } catch (Exception e) {
            throw new RuntimeException("Error validating token", e);
        }
    }

    @Override
    public String getEmailFromToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            return validateToken(token);
        } catch (Exception e) {
            throw new RuntimeException("Error validating token", e);
        }
    }

    public Instant generateExpiresAt() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
