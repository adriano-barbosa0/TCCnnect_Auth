package com.api.TCCnnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.ConnectionBuilder;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seguidores", uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_usuario_seguido"}))
@Getter
public class Seguidores {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private Usuario usuarioSeguido;  // quem está sendo seguido

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_seguido")
    private Usuario idUsuarioSeguido;  // quem está seguindo


    public static ConnectionBuilder builder() {
        return null;
    }
}
