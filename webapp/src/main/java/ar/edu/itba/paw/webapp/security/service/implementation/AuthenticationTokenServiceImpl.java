package ar.edu.itba.paw.webapp.security.service.implementation;

import ar.edu.itba.paw.webapp.security.api.model.AuthenticationTokenDetails;
import ar.edu.itba.paw.webapp.security.api.model.Authority;
import ar.edu.itba.paw.webapp.security.service.AuthenticationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

@Component
public class AuthenticationTokenServiceImpl implements AuthenticationTokenService {



    @Autowired
    TokenIssuer tokenIssuer;

    @Override
    public String issueToken(String username, Set<Authority> authorities) {
        String id = UUID.randomUUID().toString();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = issuedDate.plusSeconds(10);

        AuthenticationTokenDetails authenticationTokenDetails = new AuthenticationTokenDetails.Builder()
                .withId(id)
                .withUsername(username)
                .withAuthorities(authorities)
                .withIssuedDate(issuedDate)
                .withExpirationDate(expirationDate)
                .build();
        return tokenIssuer.issueToken(authenticationTokenDetails);
    }

    @Override
    public AuthenticationTokenDetails parseToken(String token) {
        return null;
    }

    @Override
    public String refreshToken(AuthenticationTokenDetails currentAuthenticationTokenDetails) {
        return null;
    }
}
