package com.chopervisual.chopervisual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chopervisual.chopervisual.models.Comentario;

    public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
        List<Comentario> findByHelicopteroId(Long helicopteroId);

        void deleteByHelicopteroId(Long helicopteroId);
    }