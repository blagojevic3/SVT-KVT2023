package com.example.ProjekatSVT.service;


import com.example.ProjekatSVT.dto.ReactionDTO;
import com.example.ProjekatSVT.model.*;
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

    @Autowired
    ICommentService commentService;

    @Override
    public Reaction react(ReactionDTO reactionDTO, Integer postId, Integer userId) {
        User user = userService.findById(userId);
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

    @Override
    public Reaction reactToComment(ReactionDTO reactionDTO, Integer commentId, Integer userId) {
        User user = userService.findById(userId);
        Comment comment = commentService.findOne(commentId);

        Reaction reaction = new Reaction();
        reaction.setComment(comment);
        reaction.setId(reactionDTO.getId());
        reaction.setType(EReactionType.valueOf(reactionDTO.getReactionType()));
        reaction.setUser(user);
        reaction.setTimestamp(LocalDateTime.now());

        reaction = reactionRepository.save(reaction);

        return reaction;
    }

    @Override
    public void unreact(Integer reactionId) {
        reactionRepository.deleteById(reactionId);
    }
}
