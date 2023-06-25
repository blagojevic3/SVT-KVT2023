package com.example.ProjekatSVT.service;

import com.example.ProjekatSVT.dto.UserDTO;
import com.example.ProjekatSVT.model.User;

import java.util.List;

public interface IUserService {
    User findByUsername(String username);
    User createUser(UserDTO userDTO);
    List<User> findAll();
    void save(User user);
}
