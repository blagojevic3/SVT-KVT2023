package com.example.ProjekatSVT.controller;


import com.example.ProjekatSVT.dto.GroupDTO;
import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
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
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        Group group = groupService.findGroupById(id);

        if (group == null) {
            return new ResponseEntity<>("Group not found", HttpStatus.NOT_FOUND);
        }

        groupService.deleteGroupAndAdmins(id);
        return new ResponseEntity<>("Group deleted", HttpStatus.OK);
    }

    @GetMapping("/all")
    public List<Group> loadAll(){return this.groupService.findAll();}

    @PutMapping("/edit")
    public ResponseEntity<GroupDTO> edit(@RequestBody @Validated GroupDTO editedGroup) {
        Group existingGroup = groupService.findGroupById(editedGroup.getId());

        if (existingGroup == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        // Update the existing group
        existingGroup.setName(editedGroup.getName());
        existingGroup.setDescription(editedGroup.getDescription());

        groupService.save(existingGroup);

        GroupDTO groupDTO = new GroupDTO(existingGroup);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }


}
