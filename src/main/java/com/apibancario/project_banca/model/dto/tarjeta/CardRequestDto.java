package com.apibancario.project_banca.model.dto.tarjeta;

import com.apibancario.project_banca.model.enums.TipoTarjeta;

public record CardRequestDto (
        TipoTarjeta cardType,
        String cardPin,
        Integer accountId
) {
}
