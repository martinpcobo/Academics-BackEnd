package edu.ucema.academics.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtUtilities {
    private final String secret = "2b44b0b00fd822d8ce753e54dac3dc4e06c2725f7db930f3b9924468b53194dbccdbe23d7baa5ef5fbc414ca4b2e64700bad60c5a7c45eaba56880985582fba4";
    private final Long expiration = 3600L;

    public String generateToken(String username, String id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("id", id)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(Date.from(Instant.now().plus(expiration, ChronoUnit.SECONDS)))
                .signWith(SignatureAlgorithm.HS256, secret) //Firma el token.
                .compact();
    }

    public String getToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public Long extractId(String token) {
        return Long.parseLong((String) extractAllClaims(token).get("id"));
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
