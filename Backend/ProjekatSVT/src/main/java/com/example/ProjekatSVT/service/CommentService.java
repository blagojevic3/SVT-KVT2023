package com.example.ProjekatSVT.service;


import com.example.ProjekatSVT.dto.CommentDTO;
import com.example.ProjekatSVT.model.Comment;
import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.model.User;
import com.example.ProjekatSVT.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class CommentService extends ICommentService{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @Override
    public Comment create(CommentDTO commentDTO, Integer postId, Integer commentId) {
        User user = userService.returnLoggedUser();
        Post post = postService.findPostById(postId);

        Comment comment = new Comment();
        comment.setText(commentDTO.getText());
        comment.setId(commentDTO.getId());
        comment.setTimestamp(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(post);
        comment.setParentComment(null);

        if(commentId != null) {
            Comment parentComment = this.findOne(commentId);
            comment.setParentComment(parentComment);
        }

        comment = commentRepository.save(comment);

        return comment;
    }

    @Override
    public Comment edit(CommentDTO commentDTO, Integer commentId) {
        Comment comment = this.findOne(commentId);

        comment.setText(commentDTO.getText());
        comment = this.save(comment);

        return comment;
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment delete(Integer id) {
        Comment comment = this.findOne(id);
        commentRepository.deleteById(id);
        return comment;
    }

    @Override
    public Set<Comment> getAllPostComments(Integer postId) {
        return commentRepository.getAllPostComments(postId);
    }

    @Override
    public Set<Comment> getAllUserComments(Integer userId) {
        return commentRepository.getAllUserComments(userId);
    }

    @Override
    public Comment reply(CommentDTO commentDTO, Integer commentId) {
        Comment repliesTo = this.findOne(commentId);
        Integer postId = repliesTo.getPost().getId();
        return this.create(commentDTO, postId, commentId);
    }

    @Override
    public Comment findOne(Integer commentId) {
        return commentRepository.findById(commentId).orElseGet(null);
    }

    @Override
    public Set<Comment> getAllCommentReplies(Integer commentId) {
        return commentRepository.getAllCommentReplies(commentId);
    }
}
