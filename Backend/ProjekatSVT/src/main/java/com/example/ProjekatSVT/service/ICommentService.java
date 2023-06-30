package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.CommentDTO;
import com.example.ProjekatSVT.model.Comment;

import java.util.Set;

public interface ICommentService {
    Comment create(CommentDTO commentDTO, Integer postId, Integer commentId);

    Comment edit(CommentDTO commentDTO, Integer commentId);

    Comment save(Comment comment);

    Comment delete(Integer id);

    Set<Comment> getAllPostComments(Integer postId);

    Set<Comment> getAllUserComments(Integer userId);

    Comment reply(CommentDTO commentDTO, Integer commentId);

    Comment findOne(Integer commentId);

    Set<Comment> getAllCommentReplies(Integer commentId);
}
