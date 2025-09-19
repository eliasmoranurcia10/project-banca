package com.apibancario.project_banca.model.dto.errordetail;

public record ErrorDetailDto(
        String type,
        String message,
        String dateTime
) {
}
