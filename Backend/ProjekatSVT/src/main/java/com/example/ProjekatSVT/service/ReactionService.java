package com.example.ProjekatSVT.service;


import com.example.ProjekatSVT.dto.ReactionDTO;
import com.example.ProjekatSVT.model.EReactionType;
import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.model.Reaction;
import com.example.ProjekatSVT.model.User;
import com.example.ProjekatSVT.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReactionService implements IReactionService{

    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    IUserService userService;
    @Autowired
    IPostService postService;

    @Override
    public Reaction react(ReactionDTO reactionDTO, Integer postId) {
        User user = userService.findByUsername(reactionDTO.getUsername());
        Post post = postService.findPostById(postId);

        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setId(reactionDTO.getId());
        reaction.setType(EReactionType.valueOf(reactionDTO.getReactionType()));
        reaction.setUser(user);
        reaction.setTimestamp(LocalDateTime.now());

        reaction = reactionRepository.save(reaction);

        return reaction;
    }
}
