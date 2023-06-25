package com.example.ProjekatSVT.dto;

import com.example.ProjekatSVT.model.Group;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@NoArgsConstructor
public class GroupDTO {

    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name= group.getName();
        this.description = group.getDescription();
    }
}
