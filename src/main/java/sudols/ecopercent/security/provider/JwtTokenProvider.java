package sudols.ecopercent.security.provider;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sudols.ecopercent.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
@NoArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String key;

    @Value("${jwt.signup-access-token-expiry-date}")
    private Long signupAccessTokenExpiryDate;

    @Value("${jwt.access-token-expiry-date}")
    private Long accessTokenExpiryDate;

    @Value("${jwt.refresh-token-expiry-date}")
    private Long refreshTokenExpiryData;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateAccessToken(String email, String role) {
        final long jwtExpirationInMillis = accessTokenExpiryDate;
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMillis);
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(secretKey)
                .claim("ROLE", role)
                .compact();
    }

    public String generateRefreshToken(String email) {
        final long jwtExpirationInMillis = refreshTokenExpiryData;
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMillis);
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(secretKey)
                .claim("ROLE", "USER")
                .compact();
    }

    public String generateExpiredRefreshToken(String email) {
        Date currentDate = new Date();
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(currentDate)
                .setSubject(email)
                .signWith(secretKey)
                .claim("ROLE", "USER")
                .compact();
    }

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            throw new InvalidTokenException(token);
        }
    }

    public Claims getClaimsFromTokenWithKey(String token, Key key) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new InvalidTokenException(token);
        }
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String getEmailFromRequest(HttpServletRequest request) {
        String token = getTokenFromRequest(request);
        return getEmailFromToken(token);
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromTokenWithKey(token, secretKey)
                .getSubject();
    }
}
