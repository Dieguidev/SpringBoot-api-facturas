package com.dieguidev.api_gestion_facturas.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;


import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    public SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    //*    extrae al username del token
    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    //*    extrae la fecha de expiracion del token
    public Date extractExpiration(String token) {
        return extractClaims(token, Claims::getExpiration);
    }

    /**
     * Extrae una reclamación específica (claim) de un token JWT utilizando una función de resolución de reclamaciones.
     *
     * @param token el token JWT del cual se extraerá la reclamación.
     * @param claimsResolver una función que define cómo resolver la reclamación específica de las reclamaciones extraídas.
     * @param <T> el tipo de la reclamación que se extraerá.
     * @return la reclamación específica extraída del token.
     */
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todas las reclamaciones (claims) de un token JWT.
     *
     * @param token el token JWT del cual se extraerán las reclamaciones.
     * @return un objeto Claims que contiene todas las reclamaciones del token.
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // verifica si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // genera un token para el usuario
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    // crea el token
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(SECRET_KEY)
                .compact();
    }

    // valida el token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
