package sudols.ecopercent.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sudols.ecopercent.domain.User;
import sudols.ecopercent.dto.oauth2.SignupResponse;
import sudols.ecopercent.dto.oauth2.apple.AppleSignInResponse;
import sudols.ecopercent.repository.UserRepository;
import sudols.ecopercent.security.JwtTokenProvider;
import sudols.ecopercent.service.provider.AppleOAuth2Provider;

import java.security.PublicKey;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOAuth2IosService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final sudols.ecopercent.service.provider.OAuth2ResponseProvider OAuth2ResponseProvider;
    private final AppleOAuth2Provider appleOAuth2Provider;

    public ResponseEntity<?> login(HttpServletRequest request) {
        String identityToken = jwtTokenProvider.getTokenFromRequest(request);
        PublicKey publicKey = appleOAuth2Provider.getPublicKey(identityToken);
        String email = jwtTokenProvider.getEmailFromTokenWithPublicKey(identityToken, publicKey);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            SignupResponse signupResponse = OAuth2ResponseProvider.generateAccessTokenAndGetSignupResponse(email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(signupResponse);
        }
        AppleSignInResponse appleSignInResponse = OAuth2ResponseProvider.generateTokenAndGetTokenResponse(optionalUser.get());
        return ResponseEntity.status(HttpStatus.OK).body(appleSignInResponse);
    }
}
