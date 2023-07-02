package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.UserDTO;
import com.example.ProjekatSVT.model.User;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);

    User returnLoggedUser();

    User edit(UserDTO userDTO);

    User findById(Integer id);

    User createUser(UserDTO userDTO);
    List<User> findAll();
    User save(User user);
}
