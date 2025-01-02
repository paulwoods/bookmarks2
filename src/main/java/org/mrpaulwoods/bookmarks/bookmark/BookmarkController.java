package org.mrpaulwoods.bookmarks.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

    private final BookmarkRepository bookmarkRepository;

    @GetMapping
    public ResponseEntity<Page<Bookmark>> page(Pageable pageable) {
        return ResponseEntity.ok(bookmarkRepository.findAll(pageable));
    }

}
