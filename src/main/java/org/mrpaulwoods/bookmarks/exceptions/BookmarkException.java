package org.mrpaulwoods.bookmarks.exceptions;

@SuppressWarnings("unused")
public class BookmarkException extends RuntimeException {

    public BookmarkException() {
    }

    public BookmarkException(String message) {
        super(message);
    }

    public BookmarkException(String message, Throwable cause) {
        super(message, cause);
    }

    public BookmarkException(Throwable cause) {
        super(cause);
    }

}
