package com.chopervisual.chopervisual.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chopervisual.chopervisual.models.Reaction;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, Long> {

}