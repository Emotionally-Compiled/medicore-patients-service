package medicore.patientsService.infrastructure.adapters.in.web.dto.reponse;

import java.util.List;


public record PageResponse<T>(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Integer totalElements,
        Integer totalPages,
        boolean isLast)
{
}   