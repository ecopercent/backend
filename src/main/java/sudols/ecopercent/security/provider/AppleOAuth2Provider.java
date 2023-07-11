package sudols.ecopercent.security.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import sudols.ecopercent.dto.auth.apple.AppleIdentityToken;
import sudols.ecopercent.dto.auth.apple.AppleJWKSetResponse;
import sudols.ecopercent.exception.AppleOAuth2Exception;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppleOAuth2Provider {

    private final JwtTokenProvider jwtTokenProvider;

    public PublicKey getPublicKey(String identityToken) {
        try {
            List<AppleJWKSetResponse.Key> jsonWebKeys = getJsonWebKeysFromApple();
            AppleJWKSetResponse.Key jsonWebKey = getJsonWebKeyForIdentityTokenFromJsonWebKeys(jsonWebKeys, identityToken)
                    .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

            byte[] nBytes = Base64.getUrlDecoder().decode(jsonWebKey.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(jsonWebKey.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(jsonWebKey.getKty());
            return keyFactory.generatePublic(publicKeySpec);
        } catch (Exception e) {
            throw new AppleOAuth2Exception(e);
        }
    }

    private List<AppleJWKSetResponse.Key> getJsonWebKeysFromApple() {
        return WebClient.create("https://appleid.apple.com")
                .get()
                .uri(uriBuilder -> uriBuilder.path("/auth/keys")
                        .build())
                .retrieve()
                .toEntity(AppleJWKSetResponse.class)
                .blockOptional()
                .map(ResponseEntity::getBody)
                .map(AppleJWKSetResponse::getKeys)
                .orElseThrow(() -> new RuntimeException("Failed to retrieve public key set"));
    }

    private Optional<AppleJWKSetResponse.Key> getJsonWebKeyForIdentityTokenFromJsonWebKeys
            (List<AppleJWKSetResponse.Key> jsonWebKeys, String identityToken) {
        AppleIdentityToken.Header identityTokenHeader = decodeIdentityTokenHeader(identityToken);
        return jsonWebKeys.stream()
                .filter(key -> key.getKid().equals(identityTokenHeader.getKid()) && key.getAlg().equals(identityTokenHeader.getAlg()))
                .findFirst();
    }

    private AppleIdentityToken.Header decodeIdentityTokenHeader(String identityToken) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String headerOfIdentityToken = identityToken.substring(0, identityToken.indexOf("."));
            return objectMapper.readValue(new String(Base64.getDecoder().decode(headerOfIdentityToken), StandardCharsets.UTF_8), AppleIdentityToken.Header.class);
        } catch (Exception e) {
            throw new AppleOAuth2Exception(e);
        }
    }

    public String getEmailFromTokenWithPublicKey(String token, Key key) {
        Claims claims = jwtTokenProvider.getClaimsFromTokenWithKey(token, key);
        return claims.get("email", String.class);
    }
}
