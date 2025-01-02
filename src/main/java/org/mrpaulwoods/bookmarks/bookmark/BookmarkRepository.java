package org.mrpaulwoods.bookmarks.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookmarkRepository extends JpaRepository<Bookmark, UUID> {
    Bookmark findByName(String name);
}
