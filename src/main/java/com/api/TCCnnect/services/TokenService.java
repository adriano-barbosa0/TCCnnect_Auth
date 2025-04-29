package com.api.TCCnnect.services;

import com.api.TCCnnect.model.Usuario;

import java.time.Instant;

public interface TokenService {
    String gerarToken(Usuario usuario);
    String getSubject(String tokenJWT);
    Instant expirationDate();
}
