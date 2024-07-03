package com.example.ProjekatSVT.controller;

import com.example.ProjekatSVT.searchdto.SearchQueryDTO;
import com.example.ProjekatSVT.searchmodel.GroupIndex;
import com.example.ProjekatSVT.service.impl.GroupSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search/groups")
@RequiredArgsConstructor
public class GroupSearchController {

    private final GroupSearchServiceImpl searchService;

    @PostMapping("/simple")
    public Page<GroupIndex> simpleSearch(@RequestBody SearchQueryDTO simpleSearchQuery,
                                         Pageable pageable) {
        return searchService.simpleSearch(simpleSearchQuery.keywords(), pageable);
    }

    @PostMapping("/advanced")
    public Page<GroupIndex> advancedSearch(@RequestBody SearchQueryDTO advancedSearchQuery,
                                           Pageable pageable) {
        return searchService.advancedSearch(advancedSearchQuery.keywords(), pageable);
    }

    @PostMapping("/range/{min}:{max}")
    public Page<GroupIndex> rangeSearch(@PathVariable Integer min, @PathVariable Integer max,
                                        Pageable pageable) {
        return searchService.rangeSearch(min, max, pageable);
    }



    @GetMapping("/fuzzy/name")
    public ResponseEntity<Page<GroupIndex>> searchByNameFuzzy(@RequestParam String name) {
        Page<GroupIndex> results = searchService.fuzzySearchByName(name);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/fuzzy/description")
    public ResponseEntity<Page<GroupIndex>> searchByDescriptionFuzzy(@RequestParam String description) {
        Page<GroupIndex> results = searchService.fuzzySearchByDescription(description);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/phrase/name")
    public ResponseEntity<Page<GroupIndex>> searchByNamePhrase(@RequestParam String phrase) {
        Page<GroupIndex> results = searchService.phraseSearchByName(phrase);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/phrase/description")
    public ResponseEntity<Page<GroupIndex>> searchByDescriptionPhrase(@RequestParam String phrase) {
        Page<GroupIndex> results = searchService.phraseSearchByDescription(phrase);
        return ResponseEntity.ok(results);
    }
}

