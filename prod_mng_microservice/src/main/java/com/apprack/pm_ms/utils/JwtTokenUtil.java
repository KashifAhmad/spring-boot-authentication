package com.apprack.pm_ms.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component("productJwtTokenUtil")
public class JwtTokenUtil {

    private final SecretKey secretKey;

    // Use a static Base64 encoded secret key
    private static final String SECRET_KEY = "W77YnwV7HnZt+IlWNZnnkEX3LWTy8JKIfDRpcCBvaOY=";

    public JwtTokenUtil() {
        // Decode the static key to create a SecretKey
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET_KEY), SignatureAlgorithm.HS256.getJcaName());

        byte[] decodedKey = Base64.getDecoder().decode(SECRET_KEY);
        System.out.println("DecodedSecretKeyLength: " + decodedKey.length); // Should be 32 bytes


    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey) // Use the SecretKey
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT") // Set the type explicitly
                .setHeaderParam("alg", "HS256") // Set the algorithm explicitly
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
                .signWith(secretKey) // Use the SecretKey
                .compact();

        System.out.println("Generated JWT: " + token); // Log the generated token
        System.out.println("Using SecretKey: " + Base64.getEncoder().encodeToString(secretKey.getEncoded())); // Log the key
        return token;
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);

        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
