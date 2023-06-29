package com.example.ProjekatSVT.dto;

import com.example.ProjekatSVT.model.GroupRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupRequestDTO {

    private Integer id;
    @NotBlank
    private String username;
    @NotBlank
    private Integer groupdId;
    private LocalDateTime createdAt;
    private boolean approved;

    public GroupRequestDTO(GroupRequest groupRequest) {
        this.id = groupRequest.getId();
        this.username = groupRequest.getUser().getUsername();
        this.groupdId = groupRequest.getGroup().getId();
        this.createdAt = groupRequest.getCreatedAt();
        this.approved = groupRequest.getApproved();
    }
}