package ar.edu.itba.paw.webapp.security.service.implementation;

import ar.edu.itba.paw.webapp.security.api.model.AuthenticationTokenDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class TokenIssuer {
    @Autowired
    Settings settings;

    public String issueToken(AuthenticationTokenDetails authenticationTokenDetails) {
        return Jwts.builder()
                .setId(authenticationTokenDetails.getId())
                .setIssuer(settings.getIssuer())
                .setAudience(settings.getAudience())
                .setSubject(authenticationTokenDetails.getUsername())
                .setIssuedAt(Date.from(authenticationTokenDetails.getIssuedDate().toInstant()))
                .setExpiration(Date.from(authenticationTokenDetails.getExpirationDate().toInstant()))
                .claim(settings.getAuthoritiesClaimName(), authenticationTokenDetails.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, settings.getSecret())
                .compact();
    }
}
