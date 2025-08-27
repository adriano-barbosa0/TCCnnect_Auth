package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.model.User;
import com.api.TCCnnect.services.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {
    @Value("${JWT_SECRET:12345678}")
    private String secret;

    @Override
    public String gerarToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("Api AuthUser")
                    .withSubject(user.getLogin())
                    .withClaim("Id", user.getId().toString())
                    .withExpiresAt(expirationDate())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao gerar Token", exception);
        }
    }

    @Override
    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("Api AuthUser")
                    .build()
                    .verify((tokenJWT))
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado!", exception);
        }
    }

    @Override
    public Instant expirationDate() {
        return LocalDateTime.now()
                .plusHours(3)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    public UUID extractUserId(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String userId = JWT.require(algorithm)
                    .withIssuer("Api AuthUser")
                    .build()
                    .verify(tokenJWT)
                    .getClaim("Id")
                    .asString();
            return UUID.fromString(userId);
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token inválido ou expirado!", exception);
        }
    }
}
