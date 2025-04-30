package com.api.TCCnnect.controller;

import com.api.TCCnnect.dto.DadosAutenticacao;
import com.api.TCCnnect.dto.DadosTokeJWT;
import com.api.TCCnnect.model.Usuario;
import com.api.TCCnnect.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthenticacaoController {

    private final AuthenticationManager manager;
    private  final TokenService tokenService;

    public AuthenticacaoController (AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<?> efetuarLogin(@RequestBody @Valid DadosAutenticacao dados) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.password());
        Authentication authentication = manager.authenticate(authenticationToken);
        String tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokeJWT(tokenJWT));

    }

    @GetMapping
    public String olaMundo() {
        return "Hello World Spring com token!";
    }

}
