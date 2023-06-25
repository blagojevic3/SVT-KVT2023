package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.PostDTO;
import com.example.ProjekatSVT.model.Post;

import java.util.List;

public interface IPostService {

    Post findPostByContent(String content);
    Post findPostById(Integer id);
    Post createPost(PostDTO postDTO);
    List<Post> findAll();

    void save(Post post);
    void delete(Integer id);
}
