package ar.edu.itba.paw.webapp.auth.jwt;

import ar.edu.itba.paw.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Optional;

public class JwtManager {

    private static final String ISSUER = "paw-2021b-4-api";
    private static final String AUDIENCE = "paw-2021b-4-app";

    private JwtManager() {
        throw new AssertionError();
    }

    public static Optional<User> parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                            .setSigningKey("testing") // Cambiar por key en file
                            .parseClaimsJws(token)
                            .getBody();
            User user = new User();
            user.setUsername(body.getSubject());
            user.setIsAdmin(body.get("isAdmin", Boolean.class));
            return Optional.of(user);
        } catch (JwtException | ClassCastException e) {
            return Optional.empty();
        }
    }

    public static String tokenize(String username, boolean isAdmin) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("isAdmin", isAdmin);
        return Jwts.builder()
                .setIssuer(ISSUER)
                .setAudience(AUDIENCE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, "testing") // Cambiar por key en file
                .compact();
    }

}
