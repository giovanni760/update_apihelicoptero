package com.chopervisual.chopervisual.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chopervisual.chopervisual.models.HelicopteroReaction;
import com.chopervisual.chopervisual.payload.response.ReactionContada;
import com.chopervisual.chopervisual.payload.response.ReactionCountResponse;

@Repository
public interface HelicopteroReactionRepository extends JpaRepository<HelicopteroReaction, Long> {
    @Query("SELECT cr.reaction.description, COUNT(cr) as total " +
            "FROM HelicopteroReaction cr " +
            "WHERE cr.helicoptero.id = :helicopteroId " +
            "GROUP BY cr.reaction.description " +
            "ORDER BY total DESC")
    List<Object[]> findMostVotedReactionByHelicopteroId(@Param("helicopteroId") Long helicopteroId);

    void deleteByHelicopteroId(Long helicopteroId);

 
    @Query("""
    SELECT new com.chopervisual.chopervisual.payload.response.ReactionContada(
        CAST(hr.reaction.description AS string), COUNT(hr))
    FROM HelicopteroReaction hr
    WHERE hr.helicoptero.id = :helicopteroId
    GROUP BY hr.reaction.description
""")

    List<ReactionContada> contarReaccionesPorHelicoptero(@Param("helicopteroId") Long helicopteroId);

}