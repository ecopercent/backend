package sudols.ecopercent.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Date;

@Component
@NoArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.aceess-token-expiry-date}")
    private Long accessTokenExpiryDate;

    @Value("${jwt.refresh-token-expiry-date}")
    private Long refreshTokenExpiryData;

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    public void generateTokenAndRedirectHomeWithCookie(HttpServletRequest request, HttpServletResponse response, String email) {
//        String referer = request.getHeader("Referer");
        String referer = "http://localhost:3000/";
        String accessToken = generateAccessToken(email);
        Cookie accessTokenCookie = new Cookie("access", accessToken);
        accessTokenCookie.setPath("/home");
        response.addCookie(accessTokenCookie);
        String refreshToken = generateRefreshToken(email);
        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/home");
        response.addCookie(refreshTokenCookie);
        try {
            response.sendRedirect(referer + "home");
        } catch (IOException e) {
            System.out.println("Failed redirection: " + e); // TODO: 구현. 예외처리
        }
    }

    public String generateAccessToken(String email) {
        final long jwtExpirationInMillis = accessTokenExpiryDate;
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationInMillis);
        return Jwts.builder()
                .setIssuedAt(currentDate)
                .setExpiration(expiryDate)
                .setSubject(email)
                .signWith(key)
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
                .signWith(key)
                .compact();
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getEmailFromToken(String token) {
        return getClaimsFromToken(token)
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println(e); // TODO: 로그
            return false;
        }
    }
}
