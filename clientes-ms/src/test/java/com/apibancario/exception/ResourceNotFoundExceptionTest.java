package com.apibancario.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ResourceNotFoundExceptionTest {
    @Test
    void testResourceNotFoundExceptionMessage() {
        assertThatThrownBy(
                () -> { throw new ResourceNotFoundException("Recurso no encontrado");
                }).isInstanceOf(ResourceNotFoundException.class).hasMessage("Recurso no encontrado");
    }
}
