package com.example.ProjekatSVT.repository.indexrepository;


import com.example.ProjekatSVT.model.searchmodel.GroupIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupIndexRepository extends ElasticsearchRepository<GroupIndex, Long> {
}
