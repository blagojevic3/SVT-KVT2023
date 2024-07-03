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

    GroupIndex updateGroupPostNum(Integer id);

    Page<GroupIndex> advancedSearch(List<String> expression, Pageable pageable);

    Page<GroupIndex> rangeSearch(Integer min, Integer max, Pageable pageable);

    Page<GroupIndex> searchGroupsByName(String name);

    Page<GroupIndex> searchGroupsByDescription(String description);

    @Transactional
    String indexDocument(Group group);


    Page<GroupIndex> phraseSearchByName(String phrase);

    Page<GroupIndex> phraseSearchByDescription(String phrase);

    Page<GroupIndex> fuzzySearchByName(String name);

    Page<GroupIndex> fuzzySearchByDescription(String description);
}