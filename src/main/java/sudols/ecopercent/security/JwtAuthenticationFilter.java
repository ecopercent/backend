package sudols.ecopercent.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import sudols.ecopercent.common.ErrorCode;
import sudols.ecopercent.dto.ErrorResponse;
import sudols.ecopercent.exception.ExpiredTokenException;
import sudols.ecopercent.exception.InvalidTokenException;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtTokenProvider.getTokenFromRequest(request);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                String email = jwtTokenProvider.getEmailFromToken(token);
                Authentication authentication = new PreAuthenticatedAuthenticationToken(email, null);
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException e) {
            setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
        }
    }

    public void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode);
        System.out.println("111!!");
        try{
            System.out.println("Test: " + errorResponse.toString());
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e){
            System.out.println("222!!!");
            e.printStackTrace();
        }
    }

}
