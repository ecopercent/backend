package sudols.ecopercent.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import sudols.ecopercent.domain.User;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
@NoArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String key;

    @Value("${jwt.aceess-token-expiry-date}")
    private Long accessTokenExpiryDate;

    @Value("${jwt.refresh-token-expiry-date}")
    private Long refreshTokenExpiryData;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.secretKey = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
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
            System.out.println(e); // TODO: 로깅
            return null; // TODO: 예외처리
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
            System.out.println(e); // TODO: 로깅
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
}
