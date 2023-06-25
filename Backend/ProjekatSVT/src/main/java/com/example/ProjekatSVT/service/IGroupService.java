package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.GroupDTO;
import com.example.ProjekatSVT.model.Group;

import java.util.List;

public interface IGroupService {


    Group findGroupByName(String name);

    Group findGroupById(Integer id);

    Group createGroup(GroupDTO groupDTO);

    List<Group> findAll();

    void save(Group group);

    void delete(Integer id);
}
