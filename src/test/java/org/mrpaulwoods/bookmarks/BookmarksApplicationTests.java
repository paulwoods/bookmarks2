package org.mrpaulwoods.bookmarks;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mrpaulwoods.bookmarks.bookmark.Bookmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.OK;

@SuppressWarnings("DataFlowIssue")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class BookmarksApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testBookmarkPage() {

        ResponseEntity<JacksonPage<Bookmark>> response = restTemplate.exchange(
                "/api/v1/bookmark",
                HttpMethod.GET,
                new HttpEntity<String>(null, null),
                new ParameterizedTypeReference<>() {
                }
        );

        Assertions.assertAll(
                () -> Assertions.assertEquals(OK, response.getStatusCode()),
                () -> Assertions.assertEquals("Google", response.getBody().getContent().getFirst().getName()),
                () -> Assertions.assertEquals("https://google.com", response.getBody().getContent().getFirst().getUrl())
        );

    }

    // https://stackoverflow.com/questions/34099559/how-to-consume-pageentity-response-using-spring-resttemplate
    @JsonIgnoreProperties("pageable")
    public static class JacksonPage<T> extends PageImpl<T> {
        private JacksonPage(List<T> content, int number, int size, long totalElements) {
            super(content, PageRequest.of(number, size), totalElements);
        }
    }

}
