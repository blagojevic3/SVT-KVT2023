package com.example.ProjekatSVT.controller;

import com.example.ProjekatSVT.dto.CommentDTO;
import com.example.ProjekatSVT.model.Comment;
import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/comments")
public class CommentController {

    ICommentService commentService;

    @Autowired
    public CommentController(ICommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO newComment, @PathVariable Integer postId){
        Comment createdComment = commentService.create(newComment, postId, null);

        if(createdComment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentDTO commentDTO = new CommentDTO(createdComment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public void delete(@PathVariable Integer id) {
        Comment deletedComment = commentService.delete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentDTO> edit(@PathVariable Integer id, CommentDTO editedComment) {
        Comment comment = commentService.edit(editedComment, id);

        if(editedComment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentDTO commentDTO = new CommentDTO(comment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @PostMapping("/{commentId}/reply")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<CommentDTO> reply(@RequestBody CommentDTO newComment, @PathVariable Integer commentId){
        Comment createdComment = commentService.reply(newComment, commentId);

        if(createdComment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }

        CommentDTO commentDTO = new CommentDTO(createdComment);

        return new ResponseEntity<>(commentDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}/replies")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Set<Comment> commentReplies(@PathVariable Integer commentId) {
        return commentService.getAllCommentReplies(commentId);
    }

    @GetMapping("/{postId}/all")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Set<Comment> loadAll(@PathVariable Integer postId){
        return this.commentService.getAllPostComments(postId);}

}

