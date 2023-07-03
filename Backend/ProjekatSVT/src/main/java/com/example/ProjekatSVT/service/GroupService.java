package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.GroupDTO;
import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.model.GroupAdmin;
import com.example.ProjekatSVT.model.User;
import com.example.ProjekatSVT.repository.GroupAdminRepository;
import com.example.ProjekatSVT.repository.GroupRepository;
import com.example.ProjekatSVT.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupService implements IGroupService{

    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupAdminRepository groupAdminRepository;


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

    @Override
    public Group createGroup(GroupDTO groupDTO) {
        Optional<Group> group = groupRepository.findFirstByName(groupDTO.getName());

        if (group.isPresent()) {
            return null;
        }

        // Create the Group entity
        Group newGroup = new Group();
        newGroup.setName(groupDTO.getName());
        newGroup.setDescription(groupDTO.getDescription());
        newGroup.setCreationDate(LocalDateTime.now());
        newGroup.setIsSuspended(false);

        // Get the user who is creating the group
        User creator = userService.returnLoggedUser();
        if (creator != null) {
            // Create the GroupAdmin entity
            GroupAdmin groupAdmin = new GroupAdmin();
            groupAdmin.setUser(creator);
            groupAdmin.setGroup(newGroup);

            // Add the GroupAdmin to the Group's groupAdmins set
            newGroup.getGroupAdmins().add(groupAdmin);
        } else {
            // Handle the case when the creator user is not found
            // You can choose to throw an exception or handle it differently
        }

        // Save the Group entity along with the GroupAdmin entity
        newGroup = groupRepository.save(newGroup);

        return newGroup;
    }

    @Override
    public List<Group> findAll() {
        return this.groupRepository.findAll();
    }

    @Override
    public void save(Group group) {
        groupRepository.save(group);
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
