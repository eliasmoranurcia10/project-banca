package com.apibancario.exception;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BadRequestExceptionTest {
    @Test
    void testBadRequestExceptionMessage() {
        assertThatThrownBy(
                () -> { throw new BadRequestException("Error de request");})
                .isInstanceOf(BadRequestException.class).hasMessage("Error de request");
    }
}
