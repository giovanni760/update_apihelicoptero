package com.chopervisual.chopervisual.controllers;

import com.chopervisual.chopervisual.models.Helicoptero;
import com.chopervisual.chopervisual.models.Comentario;
import com.chopervisual.chopervisual.models.User;
import com.chopervisual.chopervisual.payload.request.ComentarioRequest;
import com.chopervisual.chopervisual.payload.response.ComentarioResponse;
import com.chopervisual.chopervisual.repository.HelicopteroRepository;
import com.chopervisual.chopervisual.repository.ComentarioRepository;
import com.chopervisual.chopervisual.repository.UserRepository;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private HelicopteroRepository helicopteroRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Page<Comentario> getAllComentarios(Pageable pageable) {
        return comentarioRepository.findAll(pageable);
    }

    @GetMapping("/get/{helicopteroId}")
    public ResponseEntity<List<ComentarioResponse>> getComentariosPorHelicoptero(@PathVariable Long helicopteroId) {
        List<Comentario> comentarios = comentarioRepository.findByHelicopteroId(helicopteroId);

        List<ComentarioResponse> respuesta = comentarios.stream().map(c -> new ComentarioResponse(
                c.getId(),
                c.getContenido(),
                c.getUsuario().getUsername()
        )).toList();

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/create")
    public Comentario createComentario(@Valid @RequestBody ComentarioRequest comentarioRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = getValidUser(username);
        Helicoptero helicoptero = getValidHelicoptero(comentarioRequest.getHelicopteroId());

        Comentario comentario = new Comentario();
        comentario.setContenido(comentarioRequest.getContenido());
        comentario.setHelicoptero(helicoptero);
        comentario.setUsuario(user);

        return comentarioRepository.save(comentario);
    }

    private User getValidUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private Helicoptero getValidHelicoptero(Long helicopteroId) {
        return helicopteroRepository.findById(helicopteroId)
                .orElseThrow(() -> new RuntimeException("Helicóptero no encontrado"));
    }
}
