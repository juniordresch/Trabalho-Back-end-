// src/main/java/br.unipar.devbackend.microblog.service/PostService.java

package br.unipar.devbackend.microblog.service;

import br.unipar.devbackend.microblog.dao.PostDAO;
import br.unipar.devbackend.microblog.dao.UsuarioDAO;
import br.unipar.devbackend.microblog.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostDAO postDAO;
    private final UsuarioDAO usuarioDAO; // Necessário para validar o autor

    @Autowired
    public PostService(PostDAO postDAO, UsuarioDAO usuarioDAO) {
        this.postDAO = postDAO;
        this.usuarioDAO = usuarioDAO;
    }

    // Método para criar um novo post
    public Post criarPost(Post post) {
     // Todo Post deve estar vinculado a um usuário existente.
        if (post.getAutor() == null || post.getAutor().getId() == null) {
            throw new IllegalArgumentException("O Post deve ter um autor (usuário) válido.");
        }

        // Verifica se o Autor realmente existe no banco
        usuarioDAO.findById(post.getAutor().getId())
                .orElseThrow(() -> new RuntimeException("Autor com ID " + post.getAutor().getId() + " não encontrado."));

        // Garante que os campos iniciais estejam corretos
        post.setCreatedAt(LocalDateTime.now());
        post.setLikesCount(0L);

        return postDAO.save(post);
    }

    // Listar posts em ordem de criação
    public List<Post> listarCronologico() {
        return postDAO.findAllByOrderByCreatedAtDesc();
    }

    // Listar posts ordenados por número de curtidas
    public List<Post> listarPorRelevancia() {
        return postDAO.findAllByOrderByLikesCountDesc();
    }

    // Busca um post por ID
    public Optional<Post> buscarPorId(Long id) {
        return postDAO.findById(id);
    }
}