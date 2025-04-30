package com.api.TCCnnect.services.impl;

import com.api.TCCnnect.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticacaoService implements UserDetailsService {


    private final UsuarioRepository usuarioRepository;

    public AuthenticacaoService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails usuario = usuarioRepository.findByLogin(username);

        if (usuario == null ){
            throw new UsernameNotFoundException("Dados inválidos");
        }

        return usuario;
    }

}
