package com.chopervisual.chopervisual.controllers;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import jakarta.transaction.Transactional;

import com.chopervisual.chopervisual.payload.response.ReactionContada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chopervisual.chopervisual.models.Helicoptero;
import com.chopervisual.chopervisual.models.User;
import com.chopervisual.chopervisual.repository.HelicopteroRepository;
import com.chopervisual.chopervisual.repository.ComentarioRepository;
import com.chopervisual.chopervisual.repository.HelicopteroReactionRepository;
import com.chopervisual.chopervisual.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/helicoptero")
public class HelicopteroController {

    @Autowired
    private HelicopteroRepository helicopteroRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Page<Helicoptero> getAllHelicopteros(Pageable pageable) {
        return helicopteroRepository.findAll(pageable);
    }

    @PostMapping("/create")
    public Helicoptero createHelicoptero(@Valid @RequestBody Helicoptero helicoptero) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();

        User user = getValidUser(userId);
        Helicoptero newHelicoptero = new Helicoptero(helicoptero.getNombre(), helicoptero.getModelo(), helicoptero.getUrlFoto());
        newHelicoptero.setPostedBy(user);
        helicopteroRepository.save(newHelicoptero);

        return newHelicoptero;
    }

        @GetMapping("/buscar")
    public ResponseEntity<List<Helicoptero>> buscarHelicopteros(@RequestParam String termino) {
        List<Helicoptero> resultados = helicopteroRepository
            .findByNombreContainingIgnoreCaseOrModeloContainingIgnoreCase(termino, termino);
        return ResponseEntity.ok(resultados);
    }

        @DeleteMapping("/borrar-todo")
    public ResponseEntity<String> borrarTodosLosHelicopteros() {
        helicopteroRepository.deleteAll();
        return ResponseEntity.ok("Todos los helicópteros han sido borrados.");
    }

        @Autowired
    private HelicopteroReactionRepository helicopteroReactionRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> borrarHelicoptero(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado.");
        User user = userOpt.get();

        Optional<Helicoptero> helicopOpt = helicopteroRepository.findById(id);
        if (helicopOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Helicóptero no encontrado.");
        Helicoptero helicop = helicopOpt.get();

        if (!helicop.getPostedBy().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para borrar este helicóptero.");
        }

        comentarioRepository.deleteByHelicopteroId(helicop.getId());
        helicopteroReactionRepository.deleteByHelicopteroId(helicop.getId());
        helicopteroRepository.deleteById(id);
        return ResponseEntity.ok("Helicóptero eliminado con éxito.");
    }

  // Endpoint para obtener el conteo de reacciones por helicóptero
    @GetMapping("/reacciones/{helicopteroId}")
public ResponseEntity<List<ReactionContada>> contarReacciones(@PathVariable Long helicopteroId) {
    return ResponseEntity.ok(helicopteroReactionRepository.contarReaccionesPorHelicoptero(helicopteroId));
}
    
    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        return userOpt.orElseThrow(() -> new RuntimeException("User not found"));
    }
}
