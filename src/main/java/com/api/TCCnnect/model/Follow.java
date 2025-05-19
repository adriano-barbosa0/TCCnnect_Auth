package com.api.TCCnnect.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seguidores", uniqueConstraints = @UniqueConstraint(columnNames = {"id_usuario", "id_usuario_seguido"}))
@Getter
@Setter
public class Follow {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario")
    private User follower;  // quem está sendo seguido

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_usuario_seguido")
    private User followed;  // quem está seguindo

}
