package com.chopervisual.chopervisual.models;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
class LikeKeyFake implements Serializable {

    @Column(name = "reaction_id")
    Long reactionId;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "helicoptero_id")
    Long helicopteroId;

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHelicopteroId() {
        return helicopteroId;
    }

    public void setHelicopteroId(Long helicopteroId) {
        this.helicopteroId = helicopteroId;
    }

}