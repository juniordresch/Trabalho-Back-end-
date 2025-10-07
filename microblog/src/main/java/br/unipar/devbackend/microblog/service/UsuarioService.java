// src/main/java/br/unipar/devbackend.microblog.service/UsuarioService.java

package br.unipar.devbackend.microblog.service;

import br.unipar.devbackend.microblog.dao.UsuarioDAO;
import br.unipar.devbackend.microblog.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioDAO usuarioDAO;

    @Autowired
    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Método para criar e editar um usuário
    public Usuario salvar(Usuario usuario) {
        // Verificar se o username já existe
        if (usuario.getId() == null) {
            Optional<Usuario> existente = usuarioDAO.findByUsername(usuario.getUsername());
            if (existente.isPresent()) {
                // Lança uma exceção se o username já estiver em uso
                throw new RuntimeException("O username '" + usuario.getUsername() + "' já está em uso.");
            }
        }

        // Se o usuário já tem um ID, o Spring Data JPA fará um UPDATE (PUT).
        // Se o usuário não tem ID, fará um INSERT (POST).
        return usuarioDAO.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioDAO.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioDAO.findById(id);
    }
}