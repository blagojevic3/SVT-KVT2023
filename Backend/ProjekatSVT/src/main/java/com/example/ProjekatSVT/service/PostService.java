package com.example.ProjekatSVT.service;


import com.example.ProjekatSVT.dto.PostDTO;
import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.model.User;
import com.example.ProjekatSVT.repository.PostRepository;
import com.example.ProjekatSVT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService implements IPostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserService userService;

    @Override
    public Post findPostByContent(String content) {
        Optional<Post> post = postRepository.findPostByContent(content);
        if(!post.isEmpty()){
            return post.get();
        }

        return null;
    }

    @Override
    public Post findPostById(Integer id) {
        Optional<Post> post = postRepository.findPostById(id);
        if(!post.isEmpty()){
            return post.get();
        }

        return null;
    }

    @Override
    public Post createPost(PostDTO postDTO) {

        User user = userService.findById(postDTO.getUserId());
        Post post = new Post();

        post.setUser(user);
        post.setId(postDTO.getId());
        post.setContent(postDTO.getContent());
        post.setCreationDate(LocalDateTime.now());


        post = postRepository.save(post);
        return post;


    }

    @Override
    public List<Post> findAll() {return this.postRepository.findAll();}

    @Override
    public void save(Post post) {
        this.postRepository.save(post);
    }

    @Override
    public void delete(Integer id) {
        this.postRepository.deleteById(id);
    }
}
