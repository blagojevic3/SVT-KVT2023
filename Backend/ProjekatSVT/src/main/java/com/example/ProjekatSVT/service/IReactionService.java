package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.ReactionDTO;
import com.example.ProjekatSVT.model.Reaction;

public interface IReactionService {
    Reaction react(ReactionDTO reactionDTO, Integer postId);
}
