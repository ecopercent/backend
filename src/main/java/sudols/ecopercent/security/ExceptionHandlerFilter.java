package sudols.ecopercent.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import sudols.ecopercent.exception.ExpiredTokenException;
import sudols.ecopercent.exception.InvalidTokenException;
import sudols.ecopercent.exception.StatusCode;

import java.io.IOException;

@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredTokenException e) {
            setErrorResponse(StatusCode.EXPIRED_TOKEN, response, e);
        } catch (InvalidTokenException ex) {
            setErrorResponse(StatusCode.INVALID_TOKEN, response, ex);
        }
    }

    public void setErrorResponse(StatusCode status, HttpServletResponse response, Throwable e) {
        response.setStatus(status.getCode());
        response.setContentType("application/json");
        ErrorResponse errorResponse = new ErrorResponse(e);
        errorResponse.setMessage(e.getMessage());
        try {
            String json = errorResponse.convertToJson();
            System.out.println(json);
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}