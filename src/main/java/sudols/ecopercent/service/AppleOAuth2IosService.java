package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.auth.SignupResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.TokenResponseProvider;
import sudols.ecopercent.security.auth.AppleOAuth2Provider;

import java.security.PublicKey;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOAuth2IosService {

    private final UserRepository userRepository;
    private final TokenResponseProvider tokenResponseProvider;
    private final AppleOAuth2Provider appleOAuth2Provider;

    public ResponseEntity<?> login(HttpServletResponse response, String referer, String identityToken) {
        PublicKey publicKey = appleOAuth2Provider.getPublicKey(identityToken);
        String email = appleOAuth2Provider.getEmailFromTokenWithPublicKey(identityToken, publicKey);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            SignupResponse signupResponse = tokenResponseProvider.generateSignupAccessTokenAndGetSignupResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
        }
        tokenResponseProvider.generateTokensAndAddCookie(response, referer, optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
