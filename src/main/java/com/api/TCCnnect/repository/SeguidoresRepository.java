package com.api.TCCnnect.repository;

import com.api.TCCnnect.model.Seguidores;
import com.api.TCCnnect.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SeguidoresRepository extends JpaRepository<Seguidores, UUID> {


}
