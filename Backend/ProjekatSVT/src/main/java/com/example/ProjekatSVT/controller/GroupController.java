package com.example.ProjekatSVT.controller;


import com.example.ProjekatSVT.dto.GroupDTO;
import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/groups")
public class GroupController {


    @Autowired
    GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity<GroupDTO> create(@RequestBody @Validated GroupDTO newGroup){

        Group createdGroup = groupService.createGroup(newGroup);

        if(createdGroup == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        GroupDTO groupDTO = new GroupDTO(createdGroup);
        return new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }
    @DeleteMapping()
    public void delete(@RequestParam Integer id){groupService.delete(id);}

    @GetMapping("/all")
    public List<Group> loadAll(){return this.groupService.findAll();}

    @PutMapping("/edit")
    public ResponseEntity<GroupDTO> edit(@RequestBody @Validated GroupDTO editGroup){
        Group edit = groupService.findGroupById(editGroup.getId());
        edit.setDescription(editGroup.getDescription());
        edit.setName(editGroup.getName());
        groupService.save(edit);

        GroupDTO groupDTO = new GroupDTO(edit);
        return  new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }
}
