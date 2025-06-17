package com.chopervisual.chopervisual.models;

import jakarta.persistence.*;

@Entity
@Table(name = "helicoptero_reactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "user_id", "helicoptero_id" }),

})
public class HelicopteroReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reaction_id")
    Long reactionId;

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    @Column(name = "user_id")
    Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "helicoptero_id")
    Long helicopteroId;

    public Long getHelicoptero_id() {
        return helicopteroId;
    }

    public void setHelicoptero_id(Long helicoptero_id) {
        this.helicopteroId = helicoptero_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.user = user;
    }

    @ManyToOne
    @MapsId("helicopteroId")
    @JoinColumn(name = "helicoptero_id")
    Helicoptero helicoptero;

    public Helicoptero getHelicoptero() {
        return helicoptero;
    }

    public void setHelicoptero(Helicoptero helicoptero) {
        this.helicopteroId = helicoptero.getId();
        this.helicoptero = helicoptero;
    }

    @ManyToOne
    @MapsId("reactionId")
    @JoinColumn(name = "reaction_id")
    Reaction reaction;

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reactionId = reaction.getId();
        this.reaction = reaction;
    }

}