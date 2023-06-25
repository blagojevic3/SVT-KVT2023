package com.example.ProjekatSVT.repository;

import com.example.ProjekatSVT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findFirstByUsername(String username);

}
