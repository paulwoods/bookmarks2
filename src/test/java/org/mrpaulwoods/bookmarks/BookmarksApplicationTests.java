package org.mrpaulwoods.bookmarks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.bookmarks.bookmark.Bookmark;
import org.mrpaulwoods.bookmarks.bookmark.BookmarkRepository;
import org.mrpaulwoods.bookmarks.util.JacksonPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.*;

@SuppressWarnings("DataFlowIssue")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookmarksApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    public void testBookmarkPage() {

        ResponseEntity<JacksonPage<Bookmark>> response = restTemplate.exchange(
                "/api/v1/bookmark",
                GET,
                new HttpEntity<String>(null, null),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(OK, response.getStatusCode()),
                () -> Assertions.assertEquals("Google", response.getBody().getContent().getFirst().getName()),
                () -> Assertions.assertEquals("https://www.google.com", response.getBody().getContent().getFirst().getUrl())
        );

    }

    @Test
    public void testBookmarkCreate() {

        Bookmark ibm = new Bookmark("IBM", "https://www.ibm.com");

        ResponseEntity<Bookmark> response = restTemplate.exchange(
                "/api/v1/bookmark",
                POST,
                new HttpEntity<>(ibm, null),
                Bookmark.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(CREATED, response.getStatusCode()),
                () -> Assertions.assertEquals("IBM", response.getBody().getName()),
                () -> Assertions.assertEquals("https://www.ibm.com", response.getBody().getUrl()),
                () -> Assertions.assertTrue(response.getBody().getId().toString().length() > 10)
        );

    }

    @Test
    public void testBookmarkCreateFailsBecauseOfNullName() {

        Bookmark ibm = new Bookmark(null, "https://www.ibm.com");

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/bookmark",
                POST,
                new HttpEntity<>(ibm, null),
                String.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(BAD_REQUEST, response.getStatusCode())
        );

    }

    @Test
    public void testBookmarkRead() {

        UUID id = bookmarkRepository.findByName("Google").getId();

        ResponseEntity<Bookmark> response = restTemplate.exchange(
                "/api/v1/bookmark/%s".formatted(id),
                GET,
                new HttpEntity<>(null, null),
                Bookmark.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(OK, response.getStatusCode()),
                () -> Assertions.assertEquals("Google", response.getBody().getName()),
                () -> Assertions.assertEquals("https://www.google.com", response.getBody().getUrl()),
                () -> Assertions.assertTrue(response.getBody().getId().toString().length() > 10)
        );

    }

    @Test
    public void testBookmarkReadNotFound() {

        UUID id = UUID.fromString("79a62942-a7d6-4c14-8530-e7ff57248fa4");

        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/bookmark/%s".formatted(id),
                GET,
                new HttpEntity<>(null, null),
                String.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(NOT_FOUND, response.getStatusCode())
        );

    }

}
