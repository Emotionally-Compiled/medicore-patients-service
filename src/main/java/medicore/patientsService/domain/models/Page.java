package medicore.patientsService.domain.models;

import java.util.List;

public record Page <T>(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Integer totalElements,
        Integer totalPages,
        boolean isLast
)
{}
