package com.example.ProjekatSVT.service.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.example.ProjekatSVT.exceptionhandling.exception.MalformedQueryException;
import com.example.ProjekatSVT.model.Group;
import com.example.ProjekatSVT.repository.indexrepository.GroupIndexRepository;
import com.example.ProjekatSVT.searchmodel.DummyIndex;
import com.example.ProjekatSVT.searchmodel.GroupIndex;
import com.example.ProjekatSVT.service.interfaces.SearchGroupService;
import com.example.ProjekatSVT.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupSearchServiceImpl implements SearchGroupService {

    private final ElasticsearchOperations elasticsearchTemplate;
    private final GroupIndexRepository indexRepository;
    private final SearchService searchService;

    @Override
    public Page<GroupIndex> simpleSearch(List<String> keywords, Pageable pageable) {

        // Search for files related to groups
        Page<DummyIndex> fileResults = searchService.simpleSearch(keywords, pageable, "group");

        // Extract group ids from fileResults
        List<FieldValue> groupIdsFromFiles = fileResults.getContent().stream()
                .map(DummyIndex::getGroupId)
                .distinct()
                .map(FieldValue::of)
                .toList();


        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(keywords, groupIdsFromFiles))
                        .withPageable(pageable);

        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<GroupIndex> advancedSearch(List<String> expression, Pageable pageable) {
        if (expression.size() != 3) {
            throw new MalformedQueryException("Search query malformed.");
        }

        String operation = expression.get(1);
        expression.remove(1);
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildAdvancedSearchQuery(expression, operation))
                        .withPageable(pageable);

        return runQuery(searchQueryBuilder.build());
    }

    private Query buildSimpleSearchQuery(List<String> tokens, List<FieldValue> groupIdsFromFiles) {
        BoolQuery boolQuery = BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            tokens.forEach(token -> {
                b.should(sb -> sb.match(
                        m -> m.field("name").fuzziness(Fuzziness.ONE.asString()).query(token)));
                b.should(sb -> sb.match(m -> m.field("description").query(token)));
            });

            if (!groupIdsFromFiles.isEmpty()) {
                b.should(sb -> sb.terms(t -> t.field("id").terms(tq -> tq.value(groupIdsFromFiles))));
            }

            return b;
        })));

        System.out.println("Generated Query: " + boolQuery.toString()); // Log the query

        return boolQuery._toQuery();
    }

    private Query buildAdvancedSearchQuery(List<String> operands, String operation) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            var field1 = operands.get(0).split(":")[0];
            var value1 = operands.get(0).split(":")[1];
            var field2 = operands.get(1).split(":")[0];
            var value2 = operands.get(1).split(":")[1];

            switch (operation) {
                case "AND":
                    b.must(sb -> sb.match(
                            m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.must(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "OR":
                    b.should(sb -> sb.match(
                            m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.should(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
                case "NOT":
                    b.must(sb -> sb.match(
                            m -> m.field(field1).fuzziness(Fuzziness.ONE.asString()).query(value1)));
                    b.mustNot(sb -> sb.match(m -> m.field(field2).query(value2)));
                    break;
            }

            return b;
        })))._toQuery();
    }

    @Override
    public Page<GroupIndex> searchGroupsByName(String name) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(simpleSearchForName(name));
        return runQuery(searchQueryBuilder.build());
    }
    private Query simpleSearchForName(String name) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("name").query(name).analyzer("serbian_simple")));
            return b;
        })))._toQuery();
    }

    @Override
    public Page<GroupIndex> searchGroupsByDescription(String description) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(simpleSearchForDescription(description));
        return runQuery(searchQueryBuilder.build());
    }

    private Query simpleSearchForDescription(String description) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            b.should(sb -> sb.match(m -> m.field("description").query(description).analyzer("serbian_simple")));
            return b;
        })))._toQuery();
    }

    @Override
    @Transactional
    public String indexDocument(Group group) {
        GroupIndex newEntity = new GroupIndex(group.getId(), group.getName(), group.getDescription(), group.getCreationDate(), group.getIsSuspended(), group.getSuspendedReason());


        indexRepository.save(newEntity);

        return group.getName();
    }

    private Page<GroupIndex> runQuery(NativeQuery searchQuery) {

        var searchHits = elasticsearchTemplate.search(searchQuery, GroupIndex.class,
                IndexCoordinates.of("groups"));

        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        return (Page<GroupIndex>) SearchHitSupport.unwrapSearchHits(searchHitsPaged);
    }
}
