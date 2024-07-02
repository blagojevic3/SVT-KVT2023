package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.PostDTO;
import com.example.ProjekatSVT.model.Post;

import javax.transaction.Transactional;
import java.util.List;

public interface IPostService {

    Post findPostByContent(String content);
    Post findPostById(Integer id);
    List<Post> findAll();


    Post save(Post post);
    void delete(Integer id);
}
