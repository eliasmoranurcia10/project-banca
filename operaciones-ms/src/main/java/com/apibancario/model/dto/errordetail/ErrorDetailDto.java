package com.apibancario.model.dto.errordetail;

public record ErrorDetailDto(
        String type,
        String message,
        String dateTime
) {
}
