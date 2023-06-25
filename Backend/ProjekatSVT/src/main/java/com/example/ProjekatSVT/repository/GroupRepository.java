package com.example.ProjekatSVT.repository;


import com.example.ProjekatSVT.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public interface GroupRepository extends JpaRepository<Group,Integer> {
    Optional<Group> findFirstByName(String name);
}
