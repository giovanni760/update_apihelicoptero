package com.chopervisual.chopervisual.controllers;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chopervisual.chopervisual.models.Helicoptero;
import com.chopervisual.chopervisual.models.HelicopteroReaction;
import com.chopervisual.chopervisual.models.Reaction;
import com.chopervisual.chopervisual.models.User;
import com.chopervisual.chopervisual.payload.request.HelicopteroReactionRequest;
import com.chopervisual.chopervisual.payload.response.ReactionCountResponse;
import com.chopervisual.chopervisual.repository.HelicopteroReactionRepository;
import com.chopervisual.chopervisual.repository.HelicopteroRepository;
import com.chopervisual.chopervisual.repository.ReactionRepository;
import com.chopervisual.chopervisual.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reactions")

public class ReactionController {

    @Autowired
    private HelicopteroReactionRepository helicopteroReactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HelicopteroRepository helicopteroRepository;
    @Autowired
    private ReactionRepository reactionRepository;

    @GetMapping("/all")
    public Page<HelicopteroReaction> getReaction(Pageable pageable) {
        return helicopteroReactionRepository.findAll(pageable);
    }

    @GetMapping("/most-voted/{helicopteroId}")
    public ResponseEntity<String> getMostVotedReaction(@PathVariable Long helicopteroId) {
        List<Object[]> results = helicopteroReactionRepository.findMostVotedReactionByHelicopteroId(helicopteroId);

        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        String mostVotedReaction = results.get(0)[0].toString(); // EReaction name
        return ResponseEntity.ok(mostVotedReaction);
    }

    @PostMapping("/create")
    public HelicopteroReaction createReaction(@Valid @RequestBody HelicopteroReactionRequest helicopteroReaction) {
        System.out.println("helicopteroid : " + helicopteroReaction.getHelicopteroId());
        System.out.println("reactiontid : " + helicopteroReaction.getReactionId());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        System.out.println("userid : " + userId);

        User user = getValidUser(userId);
        System.out.println("user");

        System.out.println(user);

        HelicopteroReaction myHelicopteroReaction = new HelicopteroReaction();
        Helicoptero myHelicoptero = getValidHelicoptero(helicopteroReaction.getHelicopteroId());
        System.out.println("object helicoptero : ");
        System.out.println(myHelicoptero);

        Reaction myReaction = getValidReaction(helicopteroReaction.getReactionId());
        System.out.println("object reaction : ");
        System.out.println(myReaction);

        // myTweetReaction.setUserId(user.getId());
        // myTweetReaction.setTweetId(myTweet.getId());
        // myTweetReaction.setReactionId(myReaction.getId());

        myHelicopteroReaction.setUser(user);
        myHelicopteroReaction.setHelicoptero(myHelicoptero);
        myHelicopteroReaction.setReaction(myReaction);

        System.out.println("helicoptero reaction : ");
        System.out.println(myHelicopteroReaction.getReactionId());
        System.out.println(myHelicopteroReaction.getHelicoptero_id());

        System.out.println(myHelicopteroReaction.getUserId());

        helicopteroReactionRepository.save(myHelicopteroReaction);

        return myHelicopteroReaction;
    }



    private User getValidUser(String userId) {
        Optional<User> userOpt = userRepository.findByUsername(userId);
        if (!userOpt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userOpt.get();
    }

    private Helicoptero getValidHelicoptero(Long helicopteroId) {
        Optional<Helicoptero> helicopteroOpt = helicopteroRepository.findById(helicopteroId);
        if (!helicopteroOpt.isPresent()) {
            throw new RuntimeException("Tweet not found");
        }
        return helicopteroOpt.get();
    }

    private Reaction getValidReaction(Long reactionId) {
        Optional<Reaction> reactionOpt = reactionRepository.findById(reactionId);
        if (!reactionOpt.isPresent()) {
            throw new RuntimeException("Reaction not found");
        }
        return reactionOpt.get();
    }

}