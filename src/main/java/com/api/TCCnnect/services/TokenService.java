package com.api.TCCnnect.services;

import com.api.TCCnnect.model.User;

import java.time.Instant;
import java.util.UUID;

public interface TokenService {
    String gerarToken(User user);
    String getSubject(String tokenJWT);
    Instant expirationDate();
    UUID extractUserId(String tokenJWT);
}
