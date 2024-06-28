package com.example.ProjekatSVT.repository.indexrepository;


import com.example.ProjekatSVT.model.searchmodel.DummyIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DummyIndexRepository
        extends ElasticsearchRepository<DummyIndex, String> {
}
