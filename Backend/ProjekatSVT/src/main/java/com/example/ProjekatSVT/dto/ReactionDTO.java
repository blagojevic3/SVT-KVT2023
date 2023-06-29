package com.example.ProjekatSVT.dto;

import com.example.ProjekatSVT.model.Reaction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReactionDTO {
    private Integer id;
    @NotBlank
    private String reactionType;
    @NotBlank
    private LocalDateTime timeStamp;
    private String username;


    public ReactionDTO(Reaction reaction) {
        this.id = reaction.getId();
        this.reactionType = reaction.getType().toString();
        this.timeStamp = reaction.getTimestamp();
        this.username = reaction.getUser().getUsername();
    }
}