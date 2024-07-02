package com.example.ProjekatSVT.service.interfaces;


import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.searchmodel.GroupIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface SearchGroupService {

    Page<GroupIndex> simpleSearch(List<String> keywords, Pageable pageable);

    Page<GroupIndex> advancedSearch(List<String> expression, Pageable pageable);

    Page<GroupIndex> searchGroupsByName(String name);

    Page<GroupIndex> searchGroupsByDescription(String description);

    @Transactional
    String indexDocument(Group group);

}