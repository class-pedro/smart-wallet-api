package com.example.smart_wallet.modules.user.infrastructure.security.google;

import com.example.smart_wallet.modules.user.application.dto.GoogleUserInfo;
import com.example.smart_wallet.modules.user.application.port.out.GoogleIdentityProvider;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleIdentityProviderAdapter implements GoogleIdentityProvider {

    private final GoogleIdTokenVerifier verifier;

    public GoogleIdentityProviderAdapter(@Value("${google.oauth.client-id}") String clientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    @Override
    public GoogleUserInfo verify(String idToken) {
        try {
            GoogleIdToken googleIdToken = verifier.verify(idToken);
            if (googleIdToken == null) {
                throw new IllegalArgumentException("Invalid Google ID token");
            }

            GoogleIdToken.Payload payload = googleIdToken.getPayload();
            return new GoogleUserInfo(
                    payload.getSubject(),
                    payload.getEmail(),
                    (String) payload.get("name"));
        } catch (GeneralSecurityException | IOException e) {
            throw new IllegalArgumentException("Unable to verify Google ID token", e);
        }
    }
}
