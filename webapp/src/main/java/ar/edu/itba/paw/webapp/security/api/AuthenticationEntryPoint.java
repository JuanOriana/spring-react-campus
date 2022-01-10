package ar.edu.itba.paw.webapp.security.api;

import ar.edu.itba.paw.webapp.api.model.ApiErrorDetails;
import ar.edu.itba.paw.webapp.security.api.exception.InvalidAuthenticationTokenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        HttpStatus status;
        ApiErrorDetails errorDetails = new ApiErrorDetails();

        if (authException instanceof InvalidAuthenticationTokenException ||
            authException instanceof InsufficientAuthenticationException) {
            status = HttpStatus.UNAUTHORIZED;
            response.addHeader("WWW-Authenticate", "Basic realm=\"myRealm\"");
            response.addHeader("WWW-Authenticate", "Bearer token");
        } else {
            status = HttpStatus.FORBIDDEN;
        }
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(authException.getMessage());
        errorDetails.setStatus(status.value());
        errorDetails.setPath(request.getRequestURI());

        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        mapper.writeValue(response.getWriter(), errorDetails);
    }
}
