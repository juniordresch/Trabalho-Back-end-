// src/main/java/br/unipar/devbackend.microblog.dao/UsuarioDAO.java

package br.unipar.devbackend.microblog.dao;

import br.unipar.devbackend.microblog.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

    //Método customizado para buscar um usuário pelo username
    Optional<Usuario> findByUsername(String username);
}