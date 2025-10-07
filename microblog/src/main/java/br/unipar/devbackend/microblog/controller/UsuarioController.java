// src/main/java/br.unipar.devbackend.microblog.controller/UsuarioController.java

package br.unipar.devbackend.microblog.controller;

import br.unipar.devbackend.microblog.model.Usuario;
import br.unipar.devbackend.microblog.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ENDPOINT: POST /api/usuarios -> cria novo usuário
    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.salvar(usuario);
            // Retorna 201 Created
            return new ResponseEntity<>(novoUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Retorna 400 Bad Request em caso de username duplicado ou erro de validação
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ENDPOINT: PUT /api/usuarios -> edita usuário
    @PutMapping
    public ResponseEntity<Usuario> editarUsuario(@RequestBody Usuario usuario) {
        if (usuario.getId() == null) {
            return new ResponseEntity("ID do usuário é obrigatório para edição (PUT).", HttpStatus.BAD_REQUEST);
        }
        try {
            Usuario usuarioEditado = usuarioService.salvar(usuario);
            // Retorna 200 OK
            return new ResponseEntity<>(usuarioEditado, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Retorna 400 Bad Request
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // ENDPOINT: GET /api/usuarios -> lista todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        List<Usuario> usuarios = usuarioService.listarTodos();
        // Retorna 200 OK
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}