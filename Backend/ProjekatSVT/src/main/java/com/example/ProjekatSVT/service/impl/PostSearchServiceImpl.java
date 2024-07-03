package com.example.ProjekatSVT.service.impl;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.json.JsonData;
import com.example.ProjekatSVT.exceptionhandling.exception.LoadingException;
import com.example.ProjekatSVT.exceptionhandling.exception.MalformedQueryException;
import com.example.ProjekatSVT.exceptionhandling.exception.StorageException;
import com.example.ProjekatSVT.model.Post;
import com.example.ProjekatSVT.repository.indexrepository.PostIndexRepository;
import com.example.ProjekatSVT.searchmodel.DummyIndex;
import com.example.ProjekatSVT.searchmodel.PostIndex;
import com.example.ProjekatSVT.service.interfaces.SearchPostService;
import com.example.ProjekatSVT.service.interfaces.SearchService;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.language.detect.LanguageDetector;
import org.elasticsearch.common.unit.Fuzziness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class PostSearchServiceImpl implements SearchPostService {


    private final ElasticsearchOperations elasticsearchTemplate;
    private final PostIndexRepository indexRepository;
    private final SearchService searchService;
    private final LanguageDetector languageDetector;


    @Override
    @Transactional
    public String indexDocument(Post post) {
        PostIndex newEntity = new PostIndex(post.getId(), post.getTitle(), post.getCreationDate().toLocalDate());

        if (detectLanguage(post.getContent()).equals("SR")) {
            newEntity.setContentSr(post.getContent());
        } else {
            newEntity.setContentEn(post.getContent());
        }

        indexRepository.save(newEntity);

        return post.getTitle();
    }

    @Override
    public PostIndex updatePostLikeCount(Integer id) {
        var searchQuery = new NativeQueryBuilder()
                .withQuery(sb -> sb.match(
                        m -> m.field("id").query(id)))
                .build();

        Page<PostIndex> posts = runQuery(searchQuery);
        PostIndex post = posts.getContent().get(0);
        post.setLikeCount(post.getLikeCount() + 1);
        return indexRepository.save(post);
    }

    private String extractDocumentContent(MultipartFile multipartPdfFile) {
        String documentContent;
        try (var pdfFile = multipartPdfFile.getInputStream()) {
            var pdDocument = PDDocument.load(pdfFile);
            var textStripper = new PDFTextStripper();
            documentContent = textStripper.getText(pdDocument);
            pdDocument.close();
        } catch (IOException e) {
            throw new LoadingException("Error while trying to load PDF file content.");
        }

        return documentContent;
    }

    private String detectLanguage(String text) {
        var detectedLanguage = languageDetector.detect(text).getLanguage().toUpperCase();
        if (detectedLanguage.equals("HR")) {
            detectedLanguage = "SR";
        }

        return detectedLanguage;
    }

    private String detectMimeType(MultipartFile file) {
        var contentAnalyzer = new Tika();

        String trueMimeType;
        String specifiedMimeType;
        try {
            trueMimeType = contentAnalyzer.detect(file.getBytes());
            specifiedMimeType =
                    Files.probeContentType(Path.of(Objects.requireNonNull(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to detect mime type for file.");
        }

        if (!trueMimeType.equals(specifiedMimeType) &&
                !(trueMimeType.contains("zip") && specifiedMimeType.contains("zip"))) {
            throw new StorageException("True mime type is different from specified one, aborting.");
        }

        return trueMimeType;
    }

    @Override
    public Page<PostIndex> simpleSearch(List<String> keywords, Pageable pageable) {

        // Search for files related to posts
        Page<DummyIndex> fileResults = searchService.simpleSearch(keywords, pageable, "post");

        // Extract post ids from fileResults
        List<FieldValue> postIdsFromFiles = fileResults.getContent().stream()
                .map(DummyIndex::getPostId)
                .distinct()
                .map(FieldValue::of)
                .toList();


        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildSimpleSearchQuery(keywords, postIdsFromFiles))
                        .withPageable(pageable);

        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<PostIndex> rangeSearch(Integer min, Integer max, Pageable pageable) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(buildRangeSearchQuery(min, max))
                        .withPageable(pageable);

        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<PostIndex> advancedSearch(List<String> expression, Pageable pageable) {
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

    private Query buildSimpleSearchQuery(List<String> tokens, List<FieldValue> postIdsFromFiles) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            tokens.forEach(token -> {
                // Match Query - full-text search with fuzziness
                // Matches documents with fuzzy matching in "title" field
                b.should(sb -> sb.match(
                        m -> m.field("title").fuzziness(Fuzziness.ONE.asString()).query(token)));

                b.should(sb -> sb.match(m -> m.field("content").query(token)));

                // Match Query - full-text search in other fields
                // Matches documents with full-text search in other fields
                b.should(sb -> sb.match(m -> m.field("content_sr").query(token)));
                b.should(sb -> sb.match(m -> m.field("content_en").query(token)));

            });
            // Ensure documents have a post ID from the file search results
            if (!postIdsFromFiles.isEmpty()) {
                b.should(sb -> sb.terms(t -> t.field("id").terms(tq -> tq.value(postIdsFromFiles))));
            }

            return b;
        })))._toQuery();
    }

    private Query buildRangeSearchQuery(Integer min, Integer max) {
        return BoolQuery.of(q -> q.must(mb -> mb.bool(b -> {
            // Match Query - full-text search in other fields
            // Matches documents with full-text search in other fields
            b.must(sb -> sb.range(m -> m.field("like_count").gte(JsonData.of(min))));
            b.must(sb -> sb.range(m -> m.field("like_count").lte(JsonData.of(max))));

            return b;
        })))._toQuery();
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

    private Query phraseSearchForTitle(String phrase) {
        return MatchPhraseQuery.of(q -> q.field("title").query(phrase).analyzer("serbian_simple"))._toQuery();
    }

    private Query phraseSearchForContent(String phrase) {
        return MatchPhraseQuery.of(q -> q.field("content").query(phrase).analyzer("serbian_simple"))._toQuery();
    }

    private Query fuzzySearchForTitle(String title) {
        return MatchQuery.of(q -> q.field("title").query(title).fuzziness(Fuzziness.ONE.asString()).analyzer("serbian_simple"))._toQuery();
    }

    private Query fuzzySearchForContent(String content) {
        return MatchQuery.of(q -> q.field("content").query(content).fuzziness(Fuzziness.ONE.asString()).analyzer("serbian_simple"))._toQuery();
    }


    @Override
    public Page<PostIndex> phraseSearchByTitle(String phrase) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(phraseSearchForTitle(phrase));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<PostIndex> phraseSearchByContent(String phrase) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(phraseSearchForContent(phrase));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<PostIndex> fuzzySearchByTitle(String title) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(fuzzySearchForTitle(title));
        return runQuery(searchQueryBuilder.build());
    }

    @Override
    public Page<PostIndex> fuzzySearchByContent(String content) {
        var searchQueryBuilder =
                new NativeQueryBuilder().withQuery(fuzzySearchForContent(content));
        return runQuery(searchQueryBuilder.build());

    }



    private Page<PostIndex> runQuery(NativeQuery searchQuery) {

        var searchHits = elasticsearchTemplate.search(searchQuery, PostIndex.class,
                IndexCoordinates.of("posts"));

        var searchHitsPaged = SearchHitSupport.searchPageFor(searchHits, searchQuery.getPageable());

        return (Page<PostIndex>) SearchHitSupport.unwrapSearchHits(searchHitsPaged);
    }

}
