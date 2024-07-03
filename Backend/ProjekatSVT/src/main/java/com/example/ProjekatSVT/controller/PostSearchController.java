package com.example.ProjekatSVT.controller;


import com.example.ProjekatSVT.searchdto.SearchQueryDTO;
import com.example.ProjekatSVT.searchmodel.GroupIndex;
import com.example.ProjekatSVT.searchmodel.PostIndex;
import com.example.ProjekatSVT.service.interfaces.SearchPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/search/posts")
@RequiredArgsConstructor
public class PostSearchController {

    private final SearchPostService searchService;


    @PostMapping("/simple")
    public Page<PostIndex> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                         Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public Page<PostIndex> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                           Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }

    @PostMapping("/range/{min}:{max}")
    public Page<PostIndex> rangePostSearch(@PathVariable Integer min, @PathVariable Integer max,
                                           Pageable pageable) {
        return searchService.rangeSearch(min, max, pageable);
    }

    @GetMapping("/fuzzy/title")
    public ResponseEntity<Page<PostIndex>> searchByTitleFuzzy(@RequestParam String title) {
        Page<PostIndex> results = searchService.fuzzySearchByTitle(title);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/fuzzy/content")
    public ResponseEntity<Page<PostIndex>> searchByContentFuzzy(@RequestParam String content) {
        Page<PostIndex> results = searchService.fuzzySearchByContent(content);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/phrase/title")
    public ResponseEntity<Page<PostIndex>> searchByTitlePhrase(@RequestParam String phrase) {
        Page<PostIndex> results = searchService.phraseSearchByTitle(phrase);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/phrase/content")
    public ResponseEntity<Page<PostIndex>> searchByContentPhrase(@RequestParam String phrase) {
        Page<PostIndex> results = searchService.phraseSearchByContent(phrase);
        return ResponseEntity.ok(results);
    }
}
