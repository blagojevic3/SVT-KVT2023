package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.GroupDTO;
import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.model.GroupAdmin;
import com.example.ProjekatSVT.model.User;
import com.example.ProjekatSVT.repository.GroupAdminRepository;
import com.example.ProjekatSVT.repository.GroupRepository;
import com.example.ProjekatSVT.repository.UserRepository;
import com.example.ProjekatSVT.repository.indexrepository.GroupIndexRepository;
import com.example.ProjekatSVT.searchmodel.GroupIndex;
import com.example.ProjekatSVT.service.interfaces.SearchGroupService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class GroupService implements IGroupService{

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private GroupIndexRepository groupIndexRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupAdminRepository groupAdminRepository;

    public final SearchGroupService searchGroupService;


    public Group findGroupById(Integer id){
        Optional<Group> group = groupRepository.findById(id);
        if (!group.isEmpty()){
            return group.get();
        }
        return null;
    }


    @Override
    public Group findGroupByName(String name) {
        Optional<Group> group = groupRepository.findFirstByName(name);
        if (!group.isEmpty()){
            return group.get();
        }
        return null;
    }

//    @Override
//    public Group createGroup(GroupDTO groupDTO) {
//        Optional<Group> group = groupRepository.findFirstByName(groupDTO.getName());
//
//        if (group.isPresent()) {
//            return null;
//        }
//        GroupIndex index = new GroupIndex();
//
//
//        // Create the Group entity
//        Group newGroup = new Group();
//        newGroup.setName(groupDTO.getName());
//        newGroup.setDescription(groupDTO.getDescription());
//        newGroup.setCreationDate(LocalDateTime.now());
//        newGroup.setIsSuspended(false);
//
//        // Get the user who is creating the group
//        User creator = userService.returnLoggedUser();
//        if (creator != null) {
//            // Create the GroupAdmin entity
//            GroupAdmin groupAdmin = new GroupAdmin();
//            groupAdmin.setUser(creator);
//            groupAdmin.setGroup(newGroup);
//
//            // Add the GroupAdmin to the Group's groupAdmins set
//            newGroup.getGroupAdmins().add(groupAdmin);
//        } else {
//            // Handle the case when the creator user is not found
//            // You can choose to throw an exception or handle it differently
//        }
//
//        // Save the Group entity along with the GroupAdmin entity
//        newGroup = groupRepository.save(newGroup);
//
//        return newGroup;
//    }

//    @Override
//    @Transactional
//    public Group createGroup(GroupDTO groupDTO) {
//        Optional<Group> existingGroup = groupRepository.findFirstByName(groupDTO.getName());
//
//        if (existingGroup.isPresent()) {
//            return null; // or throw an exception indicating the group already exists
//        }
//
//        // Create the Group entity
//        Group newGroup = new Group();
//        newGroup.setName(groupDTO.getName());
//        newGroup.setDescription(groupDTO.getDescription());
//        newGroup.setCreationDate(LocalDateTime.now());
//        newGroup.setIsSuspended(false);
//
//        // Get the user who is creating the group
//        User creator = userService.returnLoggedUser();
//        if (creator != null) {
//            // Create the GroupAdmin entity
//            GroupAdmin groupAdmin = new GroupAdmin();
//            groupAdmin.setUser(creator);
//            groupAdmin.setGroup(newGroup);
//
//            // Add the GroupAdmin to the Group's groupAdmins set
//            newGroup.getGroupAdmins().add(groupAdmin);
//        } else {
//            // Handle the case when the creator user is not found
//            // You can choose to throw an exception or handle it differently
//        }
//
//        // Save the Group entity in your relational database
//        newGroup = groupRepository.save(newGroup);
//
//        // Create and save the corresponding GroupIndex in Elasticsearch
//        GroupIndex groupIndex = new GroupIndex();
//        groupIndex.setId(newGroup.getId().longValue()); // Assuming Group.id is Long in GroupIndex
//        groupIndex.setName(newGroup.getName());
//        groupIndex.setDescription(newGroup.getDescription());
//        groupIndex.setCreationDate(newGroup.getCreationDate().toLocalDate());
//
//        groupIndexRepository.save(groupIndex);
//
//        return newGroup;
//    }

    @Override
    public List<Group> findAll() {
        return this.groupRepository.findAll();
    }

    @Override
    public Group save(Group group) {
        groupRepository.save(group);
        searchGroupService.indexDocument(group);
        return group;

    }

    @Override
    public void delete(Integer id) {
        this.groupRepository.deleteById(id);
    }

    @Transactional
    public void deleteGroupAndAdmins(Integer groupId) {
        // Delete the associated group_admins entries
        Group group = groupRepository.findById(groupId).orElse(null);
        if (group != null) {
            groupAdminRepository.deleteByGroup(group);
        }

        // Delete the group
        groupRepository.deleteById(groupId);
    }
}
