package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.auth.SignupResponse;
import sudols.ecopercent.dto.auth.apple.AppleSignInResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.security.auth.AppleOAuth2Provider;
import sudols.ecopercent.security.TokenResponseProvider;

import java.security.PublicKey;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOAuth2IosService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenResponseProvider tokenResponseProvider;
    private final AppleOAuth2Provider appleOAuth2Provider;

    public ResponseEntity<?> login(HttpServletRequest request) {
        String identityToken = jwtTokenProvider.getTokenFromRequest(request);
        PublicKey publicKey = appleOAuth2Provider.getPublicKey(identityToken);
        String email = appleOAuth2Provider.getEmailFromTokenWithPublicKey(identityToken, publicKey);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            SignupResponse signupResponse = tokenResponseProvider.generateAccessTokenAndGetSignupResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
        }
        AppleSignInResponse appleSignInResponse = tokenResponseProvider.generateTokenAndGetTokenResponse(optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).body(appleSignInResponse);
    }
}
