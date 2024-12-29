package com.dieguidev.api_gestion_facturas.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;


import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
//    public SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    @Value("${security.jwt.secret-key}")
    private String SECRET_KEY;

    //*    extrae al username del token
    public String extractUsername(String token) {
        System.out.println("extractUsername");
        return extractAllClaims(token).getSubject();
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
        return Jwts.parser().verifyWith(generateKey()).build()
                .parseSignedClaims(token).getPayload();
    }

    // verifica si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // genera los claims extra del token
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }

    // crea el token
    private String createToken(Map<String, Object> claims, String subject) {
        System.out.println("subject: " + subject);
        SecretKey key = generateKey();
        System.out.println("Key generada: " + Base64.getEncoder().encodeToString(key.getEncoded()));
        return Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(key,Jwts.SIG.HS256)
                .compact();
    }

    // valida el token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        System.out.println("Username: " + username);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private SecretKey generateKey() {

        byte[] passwordDecoded = Decoders.BASE64.decode(SECRET_KEY);
//        System.out.println(new String(passwordDecoded));
//        byte[] key = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(passwordDecoded);
    }

    public String validateTokenV2(String jwt) {
        try {
            String user = extractUsername(jwt);
            System.out.println("Token valido "+ extractUsername(jwt));
            return user;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Token invalido";
        }
    }
}
