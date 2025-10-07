package br.unipar.devbackend.microblog.service;

import br.unipar.devbackend.microblog.dao.CurtidaDAO;
import br.unipar.devbackend.microblog.dao.PostDAO;
import br.unipar.devbackend.microblog.dao.UsuarioDAO;
import br.unipar.devbackend.microblog.model.Curtida;
import br.unipar.devbackend.microblog.model.Post;
import br.unipar.devbackend.microblog.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CurtidaService {

    private final CurtidaDAO curtidaDAO;
    private final PostDAO postDAO;
    private final UsuarioDAO usuarioDAO;

    @Autowired
    public CurtidaService(CurtidaDAO curtidaDAO, PostDAO postDAO, UsuarioDAO usuarioDAO) {
        this.curtidaDAO = curtidaDAO;
        this.postDAO = postDAO;
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Requisito: Curtir post e atualizar likesCount.
     * @param postId ID do post a ser curtido.
     * @param usuarioId ID do usuário que está curtindo.
     */
    @Transactional
    public void curtirPost(Long postId, Long usuarioId) {
        // 1. Validação de Existência
        Post post = postDAO.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post com ID " + postId + " não encontrado."));

        Usuario usuario = usuarioDAO.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuário com ID " + usuarioId + " não encontrado."));

        // 2. Regra de Negócio: Um usuário só pode curtir um post uma vez.
        if (curtidaDAO.findByPostIdAndUsuarioId(postId, usuarioId).isPresent()) {
            throw new RuntimeException("O usuário já curtiu este post.");
        }

        // 3. Criação da Curtida
        Curtida novaCurtida = new Curtida();
        novaCurtida.setPost(post);
        novaCurtida.setUsuario(usuario);
        novaCurtida.setCreatedAt(LocalDateTime.now());

        curtidaDAO.save(novaCurtida);

        // 4. Atualiza o likesCount do post.
        post.setLikesCount(post.getLikesCount() + 1);
        postDAO.save(post);
    }

    /**
     * Requisito: Remover curtida e atualizar likesCount.
     * @param postId ID do post.
     * @param usuarioId ID do usuário.
     * @return Mensagem de sucesso.
     */
    @Transactional
    public String removerCurtida(Long postId, Long usuarioId) { // TIPO DE RETORNO ALTERADO PARA String
        // 1. Busca a curtida existente
        Curtida curtidaExistente = curtidaDAO.findByPostIdAndUsuarioId(postId, usuarioId)
                .orElseThrow(() -> new RuntimeException("Curtida não encontrada. O usuário ainda não curtiu este post."));

        // 2. Remove a Curtida do banco
        curtidaDAO.delete(curtidaExistente);

        // 3. Atualiza o likesCount do post.
        Post post = curtidaExistente.getPost();

        if (post.getLikesCount() > 0) {
            post.setLikesCount(post.getLikesCount() - 1);
            postDAO.save(post);
        }

        // Retorna a mensagem para o Controller
        return "A curtida foi removida com sucesso. LikesCount atualizado.";
    }
}