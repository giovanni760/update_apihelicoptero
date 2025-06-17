package com.chopervisual.chopervisual.payload.request;

import jakarta.validation.constraints.NotBlank;

public class HelicopteroReactionRequest {
    private Long helicopteroId;

    public Long getHelicopteroId() {
        return helicopteroId;
    }

    public void setHelicopteroId(Long helicopteroId) {
        this.helicopteroId = helicopteroId;
    }

    //
    private Long reactionId;

    public Long getReactionId() {
        return reactionId;
    }

    public void setReactionId(Long reactionId) {
        this.reactionId = reactionId;
    }

}