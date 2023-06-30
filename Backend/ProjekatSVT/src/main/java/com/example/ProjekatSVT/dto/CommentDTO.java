package com.example.ProjekatSVT.dto;

import com.example.ProjekatSVT.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private Integer id;
    @NotBlank
    private String text;
    private LocalDateTime timeStamp;
    private Boolean isDeleted;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.timeStamp = comment.getTimestamp();
        this.isDeleted = comment.getIsDeleted();
    }

}
