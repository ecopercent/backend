package sudols.ecopercent.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtTokenProvider jwtTokenProvider;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/users/all").permitAll() // TODO: 삭제. TEST
                        .requestMatchers(HttpMethod.DELETE, "/users/all").permitAll() // TODO: 삭제. TEST
                        .requestMatchers(HttpMethod.GET, "/items/all").permitAll() // TODO: 삭제. TEST
                        .requestMatchers(HttpMethod.DELETE, "/items/all").permitAll() // TODO: 삭제. TEST
                        .requestMatchers(HttpMethod.POST, "/login/oauth2/kakao").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login/oauth2/apple/ios").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login/oauth2/apple/web").permitAll()
                        .requestMatchers(HttpMethod.POST, "/token/access").permitAll()
                        .requestMatchers(HttpMethod.POST, "/logout").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
