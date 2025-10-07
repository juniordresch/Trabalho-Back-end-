// src/main/java/br/unipar/devbackend/microblog/model/Post.java

package br.unipar.devbackend.microblog.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 280) // Limite de caracteres de 280 caracteres
    private String conteudo;

    // Relacionamento: Muitos Posts para um Usuário
    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Preenche automaticamente na criação

    @Column(name = "likes_count", nullable = false)
    private Long likesCount = 0L; // Inicia em 0
}