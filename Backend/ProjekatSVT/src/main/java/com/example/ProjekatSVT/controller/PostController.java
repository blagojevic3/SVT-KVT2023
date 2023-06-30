package com.example.ProjekatSVT.controller;

import com.example.ProjekatSVT.dto.PostDTO;
import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.model.User;
import com.example.ProjekatSVT.service.IPostService;
import com.example.ProjekatSVT.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    IPostService postService;
    @Autowired
    IUserService userService;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<PostDTO> create(Principal user, @RequestBody @Validated PostDTO newPost){

        User nesto = this.userService.findByUsername(user.getName());
        Post createdPost = postService.createPost(newPost);

        if(createdPost == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        PostDTO postDTO = new PostDTO(createdPost);
        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public void delete(@RequestParam Integer id){postService.delete(id);}

    @GetMapping("/all")
    public List<Post> loadAll(){return this.postService.findAll();}

    @PutMapping("/edit")
    public ResponseEntity<PostDTO> edit(@RequestBody @Validated PostDTO editPost){
        Post edit = postService.findPostById(editPost.getId());
        edit.setContent(editPost.getContent());
        postService.save(edit);

        PostDTO postDTO = new PostDTO(edit);
        return  new ResponseEntity<>(postDTO, HttpStatus.CREATED);
    }

}
