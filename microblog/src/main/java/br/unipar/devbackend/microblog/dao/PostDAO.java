// src/main/java/br.unipar.devbackend.microblog.dao/PostDAO.java

package br.unipar.devbackend.microblog.dao;

import br.unipar.devbackend.microblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostDAO extends JpaRepository<Post, Long> {

    // Requisito: Listar posts em ordem de criação
    List<Post> findAllByOrderByCreatedAtDesc();

    // Requisito: Listar posts ordenados por número de curtidas
    List<Post> findAllByOrderByLikesCountDesc();
}