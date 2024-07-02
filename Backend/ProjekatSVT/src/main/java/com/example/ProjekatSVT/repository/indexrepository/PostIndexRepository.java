package com.example.ProjekatSVT.repository.indexrepository;

import com.example.ProjekatSVT.searchmodel.PostIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostIndexRepository extends ElasticsearchRepository<PostIndex,Long> {
}
