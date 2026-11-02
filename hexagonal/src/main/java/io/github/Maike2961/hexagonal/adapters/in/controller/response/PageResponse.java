package io.github.Maike2961.hexagonal.adapters.in.controller.response;

import org.springframework.data.domain.Page;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        PageMetadata page
) {
    public record PageMetadata(
            int size,
            int number,
            long totalElements,
            int totalPages
    ) {}

    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                new PageMetadata(
                        page.getSize(),
                        page.getNumber() + 1, // Adjusting to 1-based index
                        page.getTotalElements(),
                        page.getTotalPages()
                )
        );
    }
}
