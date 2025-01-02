package org.mrpaulwoods.bookmarks.bookmark;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookmarkBootstrap implements CommandLineRunner {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public void run(String... args) {

        if (bookmarkRepository.count() != 0L) {
            return;
        }

        log.info("created bookmark {}", bookmarkRepository.save(new Bookmark("Google", "https://www.google.com")));
    }

}
