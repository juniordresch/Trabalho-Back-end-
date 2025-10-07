package br.unipar.devbackend.microblog.controller;

import br.unipar.devbackend.microblog.service.CurtidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class CurtidaController {

    private final CurtidaService curtidaService;

    @Autowired
    public CurtidaController(CurtidaService curtidaService) {
        this.curtidaService = curtidaService;
    }

    // ENDPOINT: POST /api/posts/{postId}/curtir/{usuarioId} -> curtir post
    @PostMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<String> curtirPost(@PathVariable Long postId, @PathVariable Long usuarioId) {
        try {
            curtidaService.curtirPost(postId, usuarioId);
            return new ResponseEntity<>("Curtida registrada com sucesso.", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ENDPOINT: DELETE /api/posts/{postId}/curtir/{usuarioId} -> remover curtida
    @DeleteMapping("/{postId}/curtir/{usuarioId}")
    public ResponseEntity<String> removerCurtida(@PathVariable Long postId, @PathVariable Long usuarioId) {
        try {
            // CAPTURA A MENSAGEM DO SERVICE
            String mensagem = curtidaService.removerCurtida(postId, usuarioId);

            // RETORNA A MENSAGEM COM STATUS 200 OK
            return new ResponseEntity<>(mensagem, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Retorna 404 Not Found se a curtida não existir
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}