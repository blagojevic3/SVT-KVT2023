package com.example.ProjekatSVT.controller;

import com.example.ProjekatSVT.dto.PostDTO;
import com.example.ProjekatSVT.model.DummyTable;
import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.model.User;
import com.example.ProjekatSVT.searchdto.DummyDocumentFileDTO;
import com.example.ProjekatSVT.service.IPostService;
import com.example.ProjekatSVT.service.IUserService;
import com.example.ProjekatSVT.service.interfaces.IndexingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {

    @Autowired
    IPostService postService;
    @Autowired
    IUserService userService;

    private final IndexingService indexingService;

    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        post.setCreationDate(LocalDateTime.now());
        Post addedPost = postService.save(post);
        return new ResponseEntity<>(addedPost, HttpStatus.CREATED);
    }

    @PostMapping("/file/add/{id}")
    public ResponseEntity<Post> addFile(@PathVariable Integer id, @ModelAttribute DummyDocumentFileDTO documentFile) {
        Post post = postService.findPostById(id);
        DummyTable file = indexingService.indexDocument(documentFile.file(), "post", id);
        post.setFile(file);
        file.setPost(post);
        postService.save(post);
        return new ResponseEntity<>(post, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

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
