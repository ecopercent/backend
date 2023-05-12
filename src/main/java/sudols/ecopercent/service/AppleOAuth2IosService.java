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
import sudols.ecopercent.service.provider.AppleOAuth2Provider;
import sudols.ecopercent.service.provider.OAuth2ResponseProvider;

import java.security.PublicKey;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOAuth2IosService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2ResponseProvider oAuth2ResponseProvider;
    private final AppleOAuth2Provider appleOAuth2Provider;

    public ResponseEntity<?> login(HttpServletRequest request) {
        String identityToken = jwtTokenProvider.getTokenFromRequest(request);
        PublicKey publicKey = appleOAuth2Provider.getPublicKey(identityToken);
        String email = appleOAuth2Provider.getEmailFromTokenWithPublicKey(identityToken, publicKey);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            SignupResponse signupResponse = oAuth2ResponseProvider.generateAccessTokenAndGetSignupResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
        }
        AppleSignInResponse appleSignInResponse = oAuth2ResponseProvider.generateTokenAndGetTokenResponse(optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).body(appleSignInResponse);
    }
}
