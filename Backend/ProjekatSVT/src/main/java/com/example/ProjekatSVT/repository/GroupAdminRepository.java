package com.example.ProjekatSVT.repository;

import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.model.GroupAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin,Integer> {
    @Modifying
    @Query(value = "DELETE FROM group_admins WHERE group_id = :groupId", nativeQuery = true)
    void deleteByGroupId(@Param("groupId") Integer groupId);

    void deleteById(Integer id);
    void deleteByGroup(Group group);
}
