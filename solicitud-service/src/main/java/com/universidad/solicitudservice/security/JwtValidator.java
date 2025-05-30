package com.universidad.solicitudservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class JwtValidator {
    
    @Value("${jwt.secret:mi-clave-secreta-super-segura-para-jwt-token-que-debe-ser-muy-larga}")
    private String jwtSecret;
    
    public boolean validarToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Header de autorización inválido");
            return false;
        }
        
        try {
            String token = authHeader.substring(7);
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            
            System.out.println("Token válido para usuario: " + claims.getSubject());
            return true;
            
        } catch (Exception e) {
            System.out.println("Error validando token JWT: " + e.getMessage());
            return false;
        }
    }
}