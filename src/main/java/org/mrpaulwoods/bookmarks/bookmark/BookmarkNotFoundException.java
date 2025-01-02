package org.mrpaulwoods.bookmarks.bookmark;

import org.mrpaulwoods.bookmarks.exceptions.BookmarkException;

import java.util.UUID;

public class BookmarkNotFoundException extends BookmarkException {

    public BookmarkNotFoundException(UUID id) {
        super("The bookmark was not found: %s".formatted(id));
    }

}
