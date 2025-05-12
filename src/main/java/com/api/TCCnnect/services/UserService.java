package com.api.TCCnnect.services;

import com.api.TCCnnect.model.Usuario;
import java.util.UUID;

public interface UserService {

    Usuario saveUser(Usuario usuario);

    Usuario findById(UUID id);
}

