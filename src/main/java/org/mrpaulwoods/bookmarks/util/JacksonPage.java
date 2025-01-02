package org.mrpaulwoods.bookmarks.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

// https://stackoverflow.com/questions/34099559/how-to-consume-pageentity-response-using-spring-resttemplate

/**
 * A custom implementation of {@link PageImpl<T>} to support deserialization
 * of paginated responses from REST APIs when using Spring's paging mechanism.
 *
 * @param <T> The type of elements in the paginated content
 */
@JsonIgnoreProperties("pageable")
public class JacksonPage<T> extends PageImpl<T> {
    private JacksonPage(List<T> content, int number, int size, long totalElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }
}
