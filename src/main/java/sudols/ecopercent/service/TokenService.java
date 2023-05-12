package sudols.ecopercent.service;


import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sudols.ecopercent.exception.ForbiddenTokenException;
import sudols.ecopercent.exception.InvalidTokenException;
import sudols.ecopercent.security.JwtTokenProvider;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final CacheService cacheService;

    public void reissueAccessToken(HttpServletRequest request, String refresh) {
        if (!jwtTokenProvider.validateToken(refresh)) {
            throw new InvalidTokenException(refresh);
        }
        String email = jwtTokenProvider.getEmailFromRequest(request);
        if (!email.equals(jwtTokenProvider.getEmailFromToken(refresh))) {
            throw new ForbiddenTokenException(refresh);
        }
        if (!refresh.equals(cacheService.getRefreshToken(email))) {
            throw new ForbiddenTokenException(refresh);
        }
        System.out.println("OKOKOK");
    }

    public void revokeRefreshToken() {

    }
}
