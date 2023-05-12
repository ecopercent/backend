package sudols.ecopercent.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sudols.ecopercent.exception.AppleOAuth2Exception;

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

    public String generateAccessTokenForSignup(String email) {
        final long jwtExpirationInMillis = signupAccessTokenExpiryDate;
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMillis);
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(secretKey)
                .claim("ROLE", "SIGNUP")
                .compact();
    }

    public String generateAccessToken(String email) {
        final long jwtExpirationInMillis = accessTokenExpiryDate;
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

    public Claims getClaimsFromTokenWithKey(String token, Key key) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            System.out.println("claims Error: " + e);
            throw new AppleOAuth2Exception(e);
        }
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromTokenWithKey(token, secretKey)
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.debug("Exception from validateToken: " + e);
            return false;
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
}
