package com.example.ProjekatSVT.dto;

import com.example.ProjekatSVT.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
public class PostDTO {

    private Integer id;
    @NotBlank
    private String content;
    private String creationDate;
    private String username;

    public PostDTO(Post post){
        this.id = post.getId();
        this.content = post.getContent();
        this.creationDate = post.getCreationDate().toString();
        this.username = post.getUser().getUsername();
    }
}
