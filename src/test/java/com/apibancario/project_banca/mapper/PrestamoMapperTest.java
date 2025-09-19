package com.apibancario.project_banca.mapper;

import com.apibancario.project_banca.model.enums.EstadoPrestamo;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PrestamoMapperTest {

    private final PrestamoMapper prestamoMapper = Mappers.getMapper(PrestamoMapper.class);

    @Test
    void testEstadoToStatus_CaseNull() {
        EstadoPrestamo estadoPrestamo = prestamoMapper.estadoToStatus(null);
        assertNull(estadoPrestamo);
    }

    @Test
    void testEstadoToStatus_CaseSolicitado() {
        EstadoPrestamo estadoPrestamo = prestamoMapper.estadoToStatus("SOLICITADO");
        assertNotNull(estadoPrestamo);
        assertEquals(EstadoPrestamo.SOLICITADO, estadoPrestamo);
    }

    @Test
    void testEstadoToStatus_CaseAprobado() {
        EstadoPrestamo estadoPrestamo = prestamoMapper.estadoToStatus("APROBADO");
        assertNotNull(estadoPrestamo);
        assertEquals(EstadoPrestamo.APROBADO, estadoPrestamo);
    }

    @Test
    void testEstadoToStatus_CaseMora() {
        EstadoPrestamo estadoPrestamo = prestamoMapper.estadoToStatus("MORA");
        assertNotNull(estadoPrestamo);
        assertEquals(EstadoPrestamo.MORA, estadoPrestamo);
    }

    @Test
    void testEstadoToStatus_CaseLiquidado() {
        EstadoPrestamo estadoPrestamo = prestamoMapper.estadoToStatus("LIQUIDADO");
        assertNotNull(estadoPrestamo);
        assertEquals(EstadoPrestamo.LIQUIDADO, estadoPrestamo);
    }

    @Test
    void testEstadoToStatus_CaseDefault() {
        EstadoPrestamo estadoPrestamo = prestamoMapper.estadoToStatus("CUALQUIER_VALOR");
        assertNull(estadoPrestamo);
    }
}
