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
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;

@SuppressWarnings("DataFlowIssue")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookmarksApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    UUID notFoundId = UUID.fromString("79a62942-a7d6-4c14-8530-e7ff57248f00");

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


        ResponseEntity<String> response = restTemplate.exchange(
                "/api/v1/bookmark/%s".formatted(notFoundId),
                GET,
                new HttpEntity<>(null, null),
                String.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(NOT_FOUND, response.getStatusCode())
        );

    }

    @Test
    public void testBookmarkUpdate() {

        Bookmark ibm = bookmarkRepository.findByName("IBM");
        ibm.setName("IBM updated");
        ibm.setUrl("https://www.ibm.com/updated");

        ResponseEntity<Bookmark> response = restTemplate.exchange(
                "/api/v1/bookmark/%s".formatted(ibm.getId()),
                PUT,
                new HttpEntity<>(ibm, null),
                Bookmark.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(OK, response.getStatusCode()),
                () -> Assertions.assertEquals("IBM updated", response.getBody().getName()),
                () -> Assertions.assertEquals("https://www.ibm.com/updated", response.getBody().getUrl()),
                () -> Assertions.assertTrue(response.getBody().getId().toString().length() > 10)
        );

    }

    @Test
    public void testBookmarkUpdateFailBecauseOfNullName() {

        Bookmark ibm = bookmarkRepository.findByName("IBM");
        ibm.setName(null);

        ResponseEntity<Bookmark> response = restTemplate.exchange(
                "/api/v1/bookmark/%s".formatted(ibm.getId()),
                PUT,
                new HttpEntity<>(ibm, null),
                Bookmark.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(BAD_REQUEST, response.getStatusCode())
        );

    }

    @Test
    public void testBookmarkUpdateFailBecauseNotFound() {

        Bookmark google = bookmarkRepository.findByName("Google");

        ResponseEntity<Bookmark> response = restTemplate.exchange(
                "/api/v1/bookmark/%s".formatted(notFoundId),
                PUT,
                new HttpEntity<>(google, null),
                Bookmark.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(NOT_FOUND, response.getStatusCode())
        );

    }

    @Test
    public void testBookmarkDelete() {

        Bookmark microsoft = bookmarkRepository.save(
                new Bookmark("Microsoft", "https://www.microsoft.com")
        );

        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/v1/bookmark/%s".formatted(microsoft.getId()),
                DELETE,
                new HttpEntity<>(null, null),
                Void.class
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(NO_CONTENT, response.getStatusCode())
        );

    }
}
