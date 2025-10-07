// src/main/java/br/unipar/devbackend/microblog/model/Curtida.java

package br.unipar.devbackend.microblog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "curtida", uniqueConstraints = {
        // Restrição de Chave Única: Um usuário só pode curtir um post uma vez
        @UniqueConstraint(columnNames = {"post_id", "usuario_id"})
})
public class Curtida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relacionamento: Muitas Curtidas para um Post
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    // Relacionamento: Muitas Curtidas para um Usuário
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}