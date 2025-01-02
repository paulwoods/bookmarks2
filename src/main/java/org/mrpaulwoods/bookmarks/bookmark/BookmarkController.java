package org.mrpaulwoods.bookmarks.bookmark;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<Bookmark> create(@Valid @RequestBody Bookmark bookmarkForm) {
        Bookmark bookmark = bookmarkRepository.save(bookmarkForm);
        String location = "/api/v1/bookmark/" + bookmark.getId();
        return ResponseEntity.created(URI.create(location)).body(bookmark);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookmark> read(@PathVariable UUID id) {
        Bookmark bookmark = bookmarkRepository.findById(id).orElseThrow(() -> new BookmarkNotFoundException(id));
        return ResponseEntity.ok(bookmark);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bookmark> update(
            @PathVariable UUID id,
            @Valid @RequestBody Bookmark bookmarkForm
    ) {
        Bookmark bookmark = bookmarkRepository.findById(id).orElseThrow(() -> new BookmarkNotFoundException(id));
        bookmark.setName(bookmarkForm.getName());
        bookmark.setUrl(bookmarkForm.getUrl());
        bookmarkRepository.save(bookmark);
        return ResponseEntity.ok(bookmark);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        Bookmark bookmark = bookmarkRepository.findById(id).orElseThrow(() -> new BookmarkNotFoundException(id));
        bookmarkRepository.delete(bookmark);
        return ResponseEntity.noContent().build();
    }

}
