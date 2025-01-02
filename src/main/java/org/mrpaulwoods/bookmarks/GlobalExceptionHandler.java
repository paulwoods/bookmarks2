package org.mrpaulwoods.bookmarks;

import org.mrpaulwoods.bookmarks.bookmark.BookmarkNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookmarkNotFoundException.class)
    @Nullable
    protected ResponseEntity<Object> bookmarkNotFoundException(BookmarkNotFoundException ex, WebRequest request) {
        ProblemDetail body = this.createProblemDetail(ex, NOT_FOUND, ex.getMessage(), null, null, request);
        return this.handleExceptionInternal(ex, body, new HttpHeaders(), NOT_FOUND, request);
    }

}
