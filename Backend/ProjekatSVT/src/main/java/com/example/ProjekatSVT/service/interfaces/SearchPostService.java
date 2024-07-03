package com.example.ProjekatSVT.service.interfaces;


import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.searchmodel.PostIndex;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
public interface SearchPostService {

    @Transactional
    String indexDocument(Post post);

    PostIndex updatePostLikeCount(Integer id);

    Page<PostIndex> simpleSearch(List<String> keywords, Pageable pageable);

    Page<PostIndex> rangeSearch(Integer min, Integer max, Pageable pageable);

    Page<PostIndex> advancedSearch(List<String> expression, Pageable pageable);

    Page<PostIndex> phraseSearchByTitle(String phrase);

    Page<PostIndex> phraseSearchByContent(String phrase);

    Page<PostIndex> fuzzySearchByTitle(String title);

    Page<PostIndex> fuzzySearchByContent(String content);
}
