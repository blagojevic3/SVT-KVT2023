package com.example.ProjekatSVT.controller;


import com.example.ProjekatSVT.dto.GroupDTO;
import com.example.ProjekatSVT.model.DummyTable;
import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.searchdto.DummyDocumentFileDTO;
import com.example.ProjekatSVT.service.GroupService;
import com.example.ProjekatSVT.service.interfaces.IndexingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/groups")
public class GroupController {


    @Autowired
    GroupService groupService;


    private final IndexingService indexingService;

    @PostMapping("/create")
    public ResponseEntity<Group> create(@RequestBody @Validated Group newGroup){


        newGroup.setCreationDate(LocalDate.now());
        newGroup.setIsSuspended(false);
        Group createdGroup = groupService.save(newGroup);

        if(createdGroup == null){
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        Group group = new Group();
//        return new ResponseEntity<>(group, HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(group);
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
    @PostMapping(value = "/file/add/{id}")
    public ResponseEntity<Group> addFile(@PathVariable Integer id, @ModelAttribute DummyDocumentFileDTO documentFile) {
        Group group = groupService.findGroupById(id);
        DummyTable file = indexingService.indexDocument(documentFile.file(), "group", id);
        group.setFile(file);
        file.setGroup(group);
        groupService.save(group);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
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
