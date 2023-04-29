package sudols.ecopercent.service.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.SignupResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleIdentityToken;
import sudols.ecopercent.dto.oauth2.apple.AppleJWKSetResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleTokenResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.security.OAuth2ResponseProvider;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOAuth2Service implements OAuth2Service {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ResponseProvider oAuth2ResponseProvider;

    @Override
    public ResponseEntity<?> login(HttpServletRequest request, HttpServletResponse response) {
        try {
            String identityToken = jwtTokenProvider.getTokenFromRequest(request);
            List<AppleJWKSetResponse.Key> jsonWebKeys = requestJsonWebKeysFromApple();
            AppleJWKSetResponse.Key jsonWebKey = getJsonWebKeyForIdentityTokenFromJsonWebKeys(jsonWebKeys, identityToken)
                    .orElseThrow(() -> new NullPointerException("Failed get public key from apple's id server."));

            byte[] nBytes = Base64.getUrlDecoder().decode(jsonWebKey.getN());
            byte[] eBytes = Base64.getUrlDecoder().decode(jsonWebKey.getE());

            BigInteger n = new BigInteger(1, nBytes);
            BigInteger e = new BigInteger(1, eBytes);

            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(n, e);
            KeyFactory keyFactory = KeyFactory.getInstance(jsonWebKey.getKty());
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
            Claims claimsOfIdentityToken = jwtTokenProvider.getClaimsFromTokenWithKey(identityToken, publicKey);
            String email = claimsOfIdentityToken.get("email", String.class);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            if (optionalUser.isEmpty()) {
                SignupResponse signupResponse = oAuth2ResponseProvider.generateSignupTokenAndReturnResponse(email);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
            }
            AppleTokenResponse appleTokenResponse = oAuth2ResponseProvider.generateAccessRefreshTokenAndReturnResponse(optionalUser.get());
            return ResponseEntity.status(HttpStatus.OK).body(appleTokenResponse);
        } catch (Exception e) {
            log.debug("Apple OAuth 로그인 중 문제 발생: " + e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // TODO: 수정.
        }
    }

    private List<AppleJWKSetResponse.Key> requestJsonWebKeysFromApple() {
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

    private Optional<AppleJWKSetResponse.Key> getJsonWebKeyForIdentityTokenFromJsonWebKeys(List<AppleJWKSetResponse.Key> jsonWebKeys, String identityToken) {
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
            log.debug("Exception from decodeIdentityTokenHeader: " + e);
            return null; // TODO: 예외처리
        }
    }
}
