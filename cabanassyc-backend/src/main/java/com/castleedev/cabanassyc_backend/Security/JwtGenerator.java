package com.castleedev.cabanassyc_backend.Security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtGenerator {
    
    public String generateToken(Authentication authentication) {
        return Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.JWT_EXPIRATION))
            .signWith(
                Keys.hmacShaKeyFor(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8)),
                SignatureAlgorithm.HS512
            )
            .compact();
    }

    public String getEmailFromJWT(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8))
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(SecurityConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT expired", ex);
        } catch (UnsupportedJwtException | MalformedJwtException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT is invalid", ex);
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT claims string is empty", ex);
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT validation failed", ex);
        }
    }
}