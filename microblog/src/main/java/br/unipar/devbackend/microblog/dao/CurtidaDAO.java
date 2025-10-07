// src/main/java/br.unipar.devbackend.microblog.dao/CurtidaDAO.java

package br.unipar.devbackend.microblog.dao;

import br.unipar.devbackend.microblog.model.Curtida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurtidaDAO extends JpaRepository<Curtida, Long> {

    // Requisito: Verificar se a curtida já existe para evitar duplicidade
    Optional<Curtida> findByPostIdAndUsuarioId(Long postId, Long usuarioId);
}