package com.example.ProjekatSVT.repository;


import com.example.ProjekatSVT.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
        Comment findFirstById(Integer id);
        @Query(value = "SELECT c FROM Comment c WHERE c.user.id = :postId")
        Set<Comment> getAllPostComments(Integer postId);
        @Query(value = "SELECT c FROM Comment c WHERE c.user.id = :userId")
        Set<Comment> getAllUserComments(Integer userId);
        @Query(value = "SELECT c FROM Comment c WHERE c.user.id = :commentId")
        Set<Comment> getAllCommentReplies(Integer commentId);
}
