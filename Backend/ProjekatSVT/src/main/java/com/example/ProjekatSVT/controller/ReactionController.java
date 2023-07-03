package com.example.ProjekatSVT.controller;

import com.example.ProjekatSVT.dto.ReactionDTO;
import com.example.ProjekatSVT.model.Reaction;
import com.example.ProjekatSVT.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/post/{postId}/{userId}")
    public ResponseEntity<ReactionDTO> reactToPost(@RequestBody ReactionDTO reactionDTO, @PathVariable Integer postId, @PathVariable Integer userId) {
        Reaction reaction = reactionService.react(reactionDTO, postId, userId);
        ReactionDTO createdReaction = new ReactionDTO(reaction);
        return ResponseEntity.ok(createdReaction);
    }

    @PostMapping("/comment/{commentId}/{userId}")
    public ResponseEntity<ReactionDTO> reactToComment(@RequestBody ReactionDTO reactionDTO, @PathVariable Integer commentId, @PathVariable Integer userId) {
        Reaction reaction = reactionService.reactToComment(reactionDTO, commentId, userId);
        ReactionDTO createdReaction = new ReactionDTO(reaction);
        return ResponseEntity.ok(createdReaction);
    }

    @DeleteMapping("/{reactionId}")
    public ResponseEntity<String> unreact(@PathVariable Integer reactionId) {
        reactionService.unreact(reactionId);
        return ResponseEntity.ok("Reaction removed successfully");
    }
}
