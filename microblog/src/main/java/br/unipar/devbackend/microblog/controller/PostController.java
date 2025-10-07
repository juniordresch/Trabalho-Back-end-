// src/main/java/br.unipar.devbackend.microblog.controller/PostController.java

package br.unipar.devbackend.microblog.controller;

import br.unipar.devbackend.microblog.model.Post;
import br.unipar.devbackend.microblog.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    // ENDPOINT: POST /api/posts -> cria post associado a um usuário
    @PostMapping
    public ResponseEntity<Post> criarPost(@RequestBody Post post) {
        try {
            Post novoPost = postService.criarPost(post);
            // Retorna 201 Created
            return new ResponseEntity<>(novoPost, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Retorna 400 Bad Request se o autor for inválido
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ENDPOINT: GET /api/posts/cronologico -> lista posts em ordem de criação
    @GetMapping("/cronologico")
    public ResponseEntity<List<Post>> listarCronologico() {
        List<Post> posts = postService.listarCronologico();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // ENDPOINT: GET /api/posts/relevancia -> lista posts ordenados por número de curtidas
    @GetMapping("/relevancia")
    public ResponseEntity<List<Post>> listarPorRelevancia() {
        List<Post> posts = postService.listarPorRelevancia();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}